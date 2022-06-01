package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.DivisionReq;
import org.crsoft.cartonplast.users.vo.res.DivisionRes;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
public interface IDivisionService {

    List<DivisionRes> findAllValidDivisions();

    DivisionRes save(DivisionReq divisionReq);

    DivisionRes update(String id, DivisionReq divisionReq);

    boolean delete(String id);
}
