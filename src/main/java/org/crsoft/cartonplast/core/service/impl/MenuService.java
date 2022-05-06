package org.crsoft.cartonplast.core.service.impl;

import lombok.extern.log4j.Log4j2;
import org.crsoft.cartonplast.core.exeption.InsertException;
import org.crsoft.cartonplast.core.exeption.NotFoundException;
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
        try{
            Menu menu = new Menu();
            menu.setLabel(menuReq.getLabel());
            menu.setData(menuReq.getData());
            menu.setIcon(menuReq.getIcon());
            menu.setUrl(menuReq.getUrl());
            menu.setRole(menuReq.getRole());

            if(!menuReq.getChild().equals(0)){
                menu.setChild(findMenuById(menuReq.getChild()));
            }

            menu.setValidFrom(new Date());
            menu.setCreatedAt(new Date());
            this.menuRepository.save(menu);
        }catch (Exception e){
            log.info("No se pudo insertar item {}",menuReq.getLabel());
            throw new InsertException("CBTMEN","No se pudo insertar item");
        }
    }

    @Override
    public Collection<MenuRes> findAllItems() throws NotFoundException {
        Collection<MenuRes>  allItems = findAllChildMenu();
        /*Collection<Menu> menuList = findAllChildMenu();
        for(Menu menu: menuList){
            allItems.add(buildMenuRes(menu));
        }*/
        return allItems;
    }

    private Menu findMenuById(Integer code) throws NotFoundException {
        Optional<Menu> menu = this.menuRepository.findById(code);
        if(menu.isPresent()){
            return menu.get();
        }else{
            log.info("No existe item con código {}",code);
            throw new NotFoundException("CBTMEN, Not Found Data");
        }
    }

    private MenuRes buildMenuRes(Menu menu){
            return MenuRes.builder()
                    .id(menu.getCode())
                    .label(menu.getLabel())
                    .icon(menu.getIcon())
                    .routerLink(Collections.singleton(menu.getUrl()))
                    .condition(menu.getCondition())
                    .build();
    }

    private Collection<Menu> findAllFatherMenu() throws NotFoundException {
        Collection<Menu> menuList = this.menuRepository.findAll();
        if(CollectionUtil.isNotEmpty(menuList)){
            return menuList.stream().filter(menu -> Objects.isNull(menu.getChild())).collect(Collectors.toSet());
        }else {
            log.info("No Existen Datos");
            throw new NotFoundException("CBTMEN, Not Found Data");
        }
    }

    private Collection<Menu> findAllOrderByCode(){
        return this.menuRepository.findAll(Sort.by(Sort.Order.asc("code")));
    }

    private MenuRes findFatherMenuRes(Collection<MenuRes> menuRes, Integer code){
        for(MenuRes menu : menuRes){
            log.info("{} y {}",menu.getId(),code);
            if(Objects.equals(menu.getId(), code)){
                log.info(menu.getLabel());
                return menu;
            }
            if(CollectionUtil.isNotEmpty(menu.getItems())){
                return findFatherMenuRes(menu.getItems(),code);
            }
        }
        return null;
    }

    private Collection<MenuRes> findAllChildMenu() throws NotFoundException {
        Collection<MenuRes>  allItems = new ArrayList<>(0);
        Collection<Menu> menuList = findAllOrderByCode();
        for(Menu menu: menuList){
            if(Objects.nonNull(menu.getChild())){
                Menu father = menu.getChild();
                MenuRes menuRes = findFatherMenuRes(allItems,father.getCode());
                Collection<MenuRes> child = new ArrayList<>(0);
                if(CollectionUtil.isNotEmpty(menuRes.getItems())){
                    child = menuRes.getItems();
                }
                child.add(buildMenuRes(menu));
                menuRes.setItems(child);
            }else{
                allItems.add(buildMenuRes(menu));
            }
        }
        return allItems;
    }
}
