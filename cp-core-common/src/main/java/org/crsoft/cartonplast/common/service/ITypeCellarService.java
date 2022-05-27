package org.crsoft.cartonplast.common.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.model.TypeCellar;
import org.crsoft.cartonplast.vo.res.TypeCellarRes;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
public interface ITypeCellarService {

    Collection<TypeCellarRes> findAllTypeCellar() throws NotFoundException;

    TypeCellar findCellarById(Integer id) throws NotFoundException;

}
