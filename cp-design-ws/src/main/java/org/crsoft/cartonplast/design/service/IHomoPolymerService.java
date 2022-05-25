package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.crsoft.cartonplast.design.vo.res.HomoPolymerRes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IHomoPolymerService {

    Collection<HomoPolymerRes> findAllValidHomopolymers() throws NotFoundException;

    void createHomopolymer(HomoPolymer homoPolymer) throws InsertException;

    HomoPolymerRes findHomoPolymerByCode(Integer code) throws NotFoundException;

    void updateHomoPolymerByCode(Integer code, HomoPolymer homoPolymer) throws NotFoundException, UpdateException;

    void deleteHomoPolymerByCode(Integer code, String userName) throws NotFoundException, UpdateException;

}
