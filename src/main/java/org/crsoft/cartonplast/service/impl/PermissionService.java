package org.crsoft.cartonplast.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.crsoft.cartonplast.common.service.RoleService;
import org.crsoft.cartonplast.exeption.InsertException;
import org.crsoft.cartonplast.exeption.NotFoundException;
import org.crsoft.cartonplast.model.Menu;
import org.crsoft.cartonplast.model.Permission;
import org.crsoft.cartonplast.repository.PermissionRepository;
import org.crsoft.cartonplast.service.IMenuService;
import org.crsoft.cartonplast.service.IPermissionService;
import org.crsoft.cartonplast.vo.RoleVo;
import org.crsoft.cartonplast.vo.req.PermissionReq;
import org.crsoft.cartonplast.vo.res.PermissionRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;

/**
 * @author jyepez on 19/5/2022
 */
@Service
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;
    private final IMenuService menuService;
    private final RoleService roleService;

    public PermissionService(PermissionRepository permissionRepository, MenuService menuService, RoleService roleService) {
        this.permissionRepository = permissionRepository;
        this.menuService = menuService;
        this.roleService = roleService;
    }

    @Override
    public void create(PermissionReq permissionReq) throws InsertException{
        try {
            this.permissionRepository.save(buildPermission(permissionReq));
        }catch (Exception e){
            throw new InsertException("CBTPER","No se pudo crear");
        }
    }

    @Override
    public void createToMenu(Menu menu) throws Exception {
        Collection<Permission> permissions = new ArrayList<>(0);
        ObjectMapper mapper = new ObjectMapper();
        Collection<RoleVo> allRole = mapper.convertValue(this.roleService.findAllRole(), new TypeReference<Collection<RoleVo>>() { });
        allRole.forEach(roleVo -> {
            Permission permission = new Permission();
            permission.setRole(roleVo.getName());
            permission.setMenu(menu);
            permissions.add(permission);
        });
        try {
            this.permissionRepository.saveAll(permissions);
        }catch (Exception e){
            throw new InsertException("CBTPER","No se pudo crear items");
        }

    }

    @Override
    public Collection<PermissionRes> findPermissionsByMenuCode(Integer code) throws NotFoundException {
        Menu menu = this.menuService.findMenuById(code);
        Collection<Permission> allByMenu = this.permissionRepository.findAllByMenu(menu);
        Collection<PermissionRes> permissionsRes = new ArrayList<>(0);
        if(CollectionUtil.isNotEmpty(allByMenu)){
            for (Permission permission: allByMenu){
                permissionsRes.add(buildPermissionRes(permission));
            }
        }else{
            throw new NotFoundException("No existe items en el menú");
        }
        return permissionsRes;
    }

    private PermissionRes buildPermissionRes(Permission permission){
        return PermissionRes.builder()
                .id(permission.getId())
                .role(permission.getRole())
                .menu(permission.getMenu().getCode())
                .create(permission.getCreate())
                .read(permission.getRead())
                .update(permission.getUpdate())
                .delete(permission.getDelete())
                .build();
    }



    private Permission buildPermission(PermissionReq permissionReq) throws NotFoundException {
        Menu menu = this.menuService.findMenuById(permissionReq.getMenu());
        Permission permission = new Permission();
        permission.setRole(permissionReq.getRole());
        permission.setCreate(permissionReq.getCreate());
        permission.setRead(permissionReq.getRead());
        permission.setUpdate(permissionReq.getUpdate());
        permission.setDelete(permissionReq.getDelete());
        permission.setMenu(menu);
        return permission;
    }
}
