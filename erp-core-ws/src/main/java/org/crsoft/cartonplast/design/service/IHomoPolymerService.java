package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.crsoft.cartonplast.vo.req.HomoPolymerReq;
import org.crsoft.cartonplast.vo.res.HomoPolymerRes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IHomoPolymerService {

    Collection<HomoPolymerRes> findAllValidHomopolymers();

    HomoPolymerRes createHomopolymer(HomoPolymerReq homoPolymer);

    HomoPolymerRes findHomoPolymerByCode(Integer code);

    HomoPolymerRes updateHomoPolymerByCode(Integer code, HomoPolymerReq homoPolymer);

    boolean deleteHomoPolymerByCode(Integer code);
}
