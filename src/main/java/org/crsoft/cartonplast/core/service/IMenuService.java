package org.crsoft.cartonplast.core.service;

import org.crsoft.cartonplast.core.exeption.InsertException;
import org.crsoft.cartonplast.core.exeption.NotFoundException;
import org.crsoft.cartonplast.core.exeption.UpdateException;
import org.crsoft.cartonplast.vo.req.MenuReq;
import org.crsoft.cartonplast.vo.res.MenuRes;

import java.util.Collection;

/**
 * @author jyepez on 5/5/2022
 */
public interface IMenuService {

    void createItem(MenuReq menuReq) throws InsertException;

    Collection<MenuRes> findAllItems() throws NotFoundException;

    void updateItem(Integer code, MenuReq menuReq) throws NotFoundException, UpdateException;

    void deleteItem(Integer code) throws NotFoundException, UpdateException;
}
