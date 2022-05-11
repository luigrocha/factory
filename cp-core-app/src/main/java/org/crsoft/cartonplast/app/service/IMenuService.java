package org.crsoft.cartonplast.app.service;

import org.crsoft.cartonplast.app.exeption.InsertException;
import org.crsoft.cartonplast.app.exeption.NotFoundException;
import org.crsoft.cartonplast.app.exeption.UpdateException;
import org.crsoft.cartonplast.vo.req.MenuReq;
import org.crsoft.cartonplast.vo.res.MenuRes;
import org.crsoft.cartonplast.vo.res.TreeNodeRes;

import java.util.Collection;

/**
 * @author jyepez on 5/5/2022
 */
public interface IMenuService {

    void createItem(MenuReq menuReq) throws InsertException;

    Collection<MenuRes> findAllItems(Collection<String> roles) throws NotFoundException;

    void updateItem(Integer code, MenuReq menuReq) throws NotFoundException, UpdateException;

    void deleteItem(Integer code) throws NotFoundException, UpdateException;

    Collection<TreeNodeRes> findAllItemsTree() throws NotFoundException;
}
