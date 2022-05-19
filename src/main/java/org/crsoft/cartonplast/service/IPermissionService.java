package org.crsoft.cartonplast.service;

import org.crsoft.cartonplast.exeption.InsertException;
import org.crsoft.cartonplast.exeption.NotFoundException;
import org.crsoft.cartonplast.model.Permission;
import org.crsoft.cartonplast.vo.req.PermissionReq;
import org.crsoft.cartonplast.vo.res.PermissionRes;

import java.util.Collection;

/**
 * @author jyepez on 19/5/2022
 */
public interface IPermissionService {

    void create(PermissionReq permissionReq) throws InsertException;

    Collection<PermissionRes> findPermissionsByMenuCode(Integer code) throws NotFoundException;

}
