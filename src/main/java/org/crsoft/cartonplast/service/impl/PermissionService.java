package org.crsoft.cartonplast.service.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.common.service.RoleService;
import org.crsoft.cartonplast.enums.RoleEnum;
import org.crsoft.cartonplast.model.CatalogPermission;
import org.crsoft.cartonplast.model.Menu;
import org.crsoft.cartonplast.model.Permission;
import org.crsoft.cartonplast.model.TypePermission;
import org.crsoft.cartonplast.repository.CatalogPermissionRepository;
import org.crsoft.cartonplast.repository.PermissionRepository;
import org.crsoft.cartonplast.repository.TypePermissionRepository;
import org.crsoft.cartonplast.service.IMenuService;
import org.crsoft.cartonplast.service.IPermissionService;
import org.crsoft.cartonplast.vo.RoleVo;
import org.crsoft.cartonplast.vo.req.TypePermissionReq;
import org.crsoft.cartonplast.vo.res.PermissionRes;
import org.crsoft.cartonplast.vo.res.TypePermissionRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;

/**
 * @author jyepez on 19/5/2022
 */
@Service
public class PermissionService implements IPermissionService {

    private final PermissionRepository permissionRepository;
    private final TypePermissionRepository typePermissionRepository;
    private final CatalogPermissionRepository catalogPermissionRepository;
    private final IMenuService menuService;
    private final RoleService roleService;

    public PermissionService(
            PermissionRepository permissionRepository,
            TypePermissionRepository typePermissionRepository,
            CatalogPermissionRepository catalogPermissionRepository, MenuService menuService, RoleService roleService) {
        this.permissionRepository = permissionRepository;
        this.typePermissionRepository = typePermissionRepository;
        this.catalogPermissionRepository = catalogPermissionRepository;
        this.menuService = menuService;
        this.roleService = roleService;
    }

    @Override
    public void createToMenu(Menu menu) throws Exception {
        Collection<Permission> permissions = new ArrayList<>(0);
        ObjectMapper mapper = new ObjectMapper();
        Collection<RoleVo> allRole = mapper.convertValue(this.roleService.findAllRole(), new TypeReference<>() {
        });
        allRole.forEach(roleVo -> {
            Permission permission = new Permission();
            permission.setRole(roleVo.getName());
            permission.setMenu(menu);
            permissions.add(permission);
        });
        try {
            this.permissionRepository.saveAll(permissions);
            Collection<TypePermission> typePermissions = new ArrayList<>(0);
            Collection<CatalogPermission> catalogPermissions = this.catalogPermissionRepository.findAll();

            for (Permission permission : permissions) {
                for (CatalogPermission catalogPermission : catalogPermissions) {
                    TypePermission typePermission = new TypePermission();
                    typePermission.setPermission(permission);
                    typePermission.setCatalog(catalogPermission);
                    typePermissions.add(typePermission);
                }
                this.typePermissionRepository.saveAll(typePermissions);
            }

        } catch (Exception e) {
            throw new InsertException("CBTPER", "No se pudo crear items");
        }

    }

    @Override
    public Collection<PermissionRes> findPermissionsByMenuCode(Integer code) throws NotFoundException {
        Collection<PermissionRes> permissionsRes = new ArrayList<>(0);
        for (Permission permission : getPermissionByMenuCode(code)) {
            permissionsRes.add(buildPermissionRes(permission));
        }
        return permissionsRes;
    }

    @Override
    public void updatePermissionByMenuCode(Collection<TypePermissionReq> typePermissionReqs, Integer codeMenu, Integer codePermission) throws NotFoundException, UpdateException {
        Menu menu = this.menuService.findMenuById(codeMenu);
        Permission permission = this.permissionRepository.findByMenuAndId(menu, codePermission);
        Collection<TypePermission> typePermissions = this.typePermissionRepository.findAllByPermission(permission);

        for (TypePermissionReq typePermissionReq : typePermissionReqs) {
            try {
                TypePermission type = typePermissions.stream()
                        .filter(typePermission -> typePermission.getCatalog().getId().equals(typePermissionReq.getId()))
                        .findAny().orElse(null);
                Objects.requireNonNull(type).setFlag(typePermissionReq.getFlag());
                this.typePermissionRepository.save(type);
            } catch (Exception e) {
                throw new UpdateException("CBTPER_TYPE", "No se pudo actualizar permiso");
            }
        }
    }

    @Override
    public Collection<TypePermissionRes> findPermissionsPage(String url, Collection<String> roles) throws NotFoundException {
        Menu menu = this.menuService.findMenuByUrl(url);
        String role = getPriorityRole(roles);
        Permission permission = this.permissionRepository.findByMenuAndRole(menu, role);
        Collection<TypePermission> typePermissions = this.typePermissionRepository.findAllByPermission(permission);
        return buildTypePermissionRes(typePermissions);
    }

    private String getPriorityRole(Collection<String> roles) {
        if (roles.contains(RoleEnum.ADMIN.getRole())) {
            return RoleEnum.ADMIN.getRole();
        } else if (roles.contains(RoleEnum.SUPERVISOR.getRole())) {
            return RoleEnum.SUPERVISOR.getRole();
        }
        return RoleEnum.USER.getRole();
    }

    private Collection<Permission> getPermissionByMenuCode(Integer code) throws NotFoundException {
        Menu menu = this.menuService.findMenuById(code);
        Collection<Permission> allByMenu = this.permissionRepository.findAllByMenu(menu);
        if (CollectionUtil.isNotEmpty(allByMenu)) {
            return allByMenu;
        } else {
            throw new NotFoundException("No existen permisos con el menú");
        }
    }

    private PermissionRes buildPermissionRes(Permission permission) {
        Collection<TypePermission> typePermissions = this.typePermissionRepository.findAllByPermission(permission);
        return PermissionRes.builder()
                .id(permission.getId())
                .role(permission.getRole())
                .typePermission(buildTypePermissionRes(typePermissions))
                .build();
    }

    private Collection<TypePermissionRes> buildTypePermissionRes(Collection<TypePermission> typePermissions) {
        Collection<TypePermissionRes> typePermissionRes = new ArrayList<>(0);
        for (TypePermission typePermission : typePermissions) {
            CatalogPermission catalogPermission = typePermission.getCatalog();
            typePermissionRes.add(TypePermissionRes.builder()
                    .id(catalogPermission.getId())
                    .name(catalogPermission.getName())
                    .flag(typePermission.getFlag())
                    .build());
        }
        return typePermissionRes;
    }
}
