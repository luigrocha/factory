package org.crsoft.cartonplast.service;

import org.crsoft.cartonplast.exeption.NotFoundException;
import org.crsoft.cartonplast.exeption.UpdateException;
import org.crsoft.cartonplast.model.Menu;
import org.crsoft.cartonplast.vo.req.TypePermissionReq;
import org.crsoft.cartonplast.vo.res.PermissionRes;

import java.util.Collection;

/**
 * @author jyepez on 19/5/2022
 */
public interface IPermissionService {

    void createToMenu(Menu menu) throws Exception;

    Collection<PermissionRes> findPermissionsByMenuCode(Integer code) throws NotFoundException;

    void updatePermissionByMenuCode(Collection<TypePermissionReq> typePermissionReqs, Integer code) throws NotFoundException, UpdateException;

}
