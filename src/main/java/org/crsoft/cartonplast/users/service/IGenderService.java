package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.GenderReq;
import org.crsoft.cartonplast.users.vo.res.GenderRes;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
public interface IGenderService {

    List<GenderRes> findAllValidGenders();
    GenderRes save(GenderReq genderReq);
    GenderRes update(String id, GenderReq genderReq);
    boolean delete(String id);
}
