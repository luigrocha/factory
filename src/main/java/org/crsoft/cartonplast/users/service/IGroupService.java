package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.GroupReq;
import org.crsoft.cartonplast.users.vo.res.GroupRes;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
public interface IGroupService {

    List<GroupRes> findAllValidGroups();

    GroupRes save(GroupReq groupReq);

    GroupRes update(String id, GroupReq groupReq);

    boolean delete(String id);
}
