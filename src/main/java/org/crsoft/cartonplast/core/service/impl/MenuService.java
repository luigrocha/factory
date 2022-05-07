package org.crsoft.cartonplast.core.service.impl;

import lombok.extern.log4j.Log4j2;
import org.crsoft.cartonplast.core.exeption.InsertException;
import org.crsoft.cartonplast.core.exeption.NotFoundException;
import org.crsoft.cartonplast.core.exeption.UpdateException;
import org.crsoft.cartonplast.core.model.Menu;
import org.crsoft.cartonplast.core.repository.MenuRepository;
import org.crsoft.cartonplast.core.service.IMenuService;
import org.crsoft.cartonplast.vo.req.MenuReq;
import org.crsoft.cartonplast.vo.res.MenuRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * @author jyepez on 5/5/2022
 */
@Service
@Log4j2
public class MenuService implements IMenuService {

    private final MenuRepository menuRepository;

    public MenuService(MenuRepository menuRepository) {
        this.menuRepository = menuRepository;
    }

    @Override
    public void createItem(MenuReq menuReq) throws InsertException {
        try {
            Menu menu = new Menu();
            menu.setLabel(menuReq.getLabel());
            menu.setData(menuReq.getData());
            menu.setIcon(menuReq.getIcon());
            menu.setUrl(menuReq.getUrl());
            menu.setRole(menuReq.getRole());

            if (!menuReq.getChild().equals(0)) {
                Menu menuById = findMenuById(menuReq.getChild());
                menu.setChild(menuById);
                updateOrder(menuById,menuReq.getOrder());
            }else{
                updateOrder(null,menuReq.getOrder());
            }

            menu.setOrder(menuReq.getOrder());
            menu.setValidFrom(new Date());
            menu.setCreatedAt(new Date());
            this.menuRepository.save(menu);

        } catch (Exception e) {
            log.info("No se pudo insertar item {}", menuReq.getLabel());
            throw new InsertException("CBTMEN", "No se pudo insertar item");
        }
    }

    @Override
    public Collection<MenuRes> findAllItems() throws NotFoundException {
        Collection<MenuRes> allItems = new ArrayList<>(0);
        Collection<Menu> menuList = findAllOrderByCode();
        for (Menu menu : menuList) {
            if (Objects.nonNull(menu.getChild())) {
                buildItemsMenuRes(allItems, menu.getChild().getCode(), menu);
            } else {
                allItems.add(buildMenuRes(menu));
            }
        }
        allItems = allItems.stream().sorted(Comparator.comparing(MenuRes::getOrder)).collect(Collectors.toList());
        return allItems;
    }

    @Override
    public void updateItem(Integer code, MenuReq menuReq) throws NotFoundException, UpdateException {
        Menu item = findMenuById(code);
        try {
            item.setLabel(menuReq.getLabel());
            item.setData(menuReq.getData());
            item.setIcon(menuReq.getIcon());
            item.setUrl(menuReq.getUrl());
            item.setRole(menuReq.getRole());

            if (!menuReq.getChild().equals(0) && !item.getOrder().equals(menuReq.getOrder())) {
                updateOrder(item,menuReq.getOrder());
            }else if(!item.getOrder().equals(menuReq.getOrder())){
                updateOrder(null,menuReq.getOrder());
            }
            item.setOrder(menuReq.getOrder());

            item.setUpdateAt(new Date());
            this.menuRepository.save(item);
        }catch (Exception e){
            throw new UpdateException("CBTMEN","No se pudo actualizar");
        }
    }

    @Override
    public void deleteItem(Integer code) throws NotFoundException, UpdateException {
        Menu item = findMenuById(code);
        try {
            this.menuRepository.delete(item);
        }catch (Exception e){
            throw new UpdateException("CBTMEN","No se pudo eliminar");
        }
    }

    private void updateOrder(Menu child, Integer order) throws UpdateException, NotFoundException {
        Collection<Menu> allByChild = this.menuRepository.findAllByChild(child);
        Integer sequence = order;
        if(CollectionUtil.isNotEmpty(allByChild)) {
            try {
                for (Menu item : allByChild) {
                    if (item.getOrder() >= order) {
                        sequence++;
                        item.setOrder(sequence);
                        this.menuRepository.save(item);
                    }
                }
            } catch (Exception e) {
                throw new UpdateException("CBTMEN", "No se pudo actualizar el orden");
            }
        }else {
            throw new NotFoundException("No se encontraron datos");
        }
    }

    private Menu findMenuById(Integer code) throws NotFoundException {
        Optional<Menu> menu = this.menuRepository.findById(code);
        if (menu.isPresent()) {
            return menu.get();
        } else {
            log.info("No existe item con código {}", code);
            throw new NotFoundException("CBTMEN, Not Found Data");
        }
    }

    private MenuRes buildMenuRes(Menu menu) {
        return MenuRes.builder()
                .id(menu.getCode())
                .label(menu.getLabel())
                .icon(menu.getIcon())
                .routerLink(Collections.singleton(menu.getUrl()))
                .condition(menu.getCondition())
                .order(menu.getOrder())
                .build();
    }

    private Collection<Menu> findAllOrderByCode() {
        return this.menuRepository.findAll(Sort.by(Sort.Order.asc("code")));
    }

    private void buildItemsMenuRes(Collection<MenuRes> menuRes, Integer code, Menu menu) {
        for (MenuRes item : menuRes) {
            if (item.getId().equals(code)) {
                Collection<MenuRes> child = new ArrayList<>(0);
                if (CollectionUtil.isNotEmpty(item.getItems())) {
                    child = item.getItems();
                }
                child.add(buildMenuRes(menu));

                child = child.stream().sorted(Comparator.comparing(MenuRes::getOrder)).collect(Collectors.toList());

                item.setItems(child);
            }
            if (CollectionUtil.isNotEmpty(item.getItems())) {
                buildItemsMenuRes(item.getItems(), code, menu);
            }
        }
    }

}
