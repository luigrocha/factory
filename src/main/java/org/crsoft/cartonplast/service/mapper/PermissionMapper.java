package org.crsoft.cartonplast.service.mapper;

import org.crsoft.cartonplast.model.Permission;
import org.crsoft.cartonplast.vo.res.PermissionRes;
import org.mapstruct.Mapper;

/**
 * @author jyepez on 19/5/2022
 */
// @Mapper(componentModel = "spring")
public interface PermissionMapper {

    PermissionRes permissionToPermissionRes(Permission permission);
}
