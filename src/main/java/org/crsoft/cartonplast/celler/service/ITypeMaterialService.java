package org.crsoft.cartonplast.celler.service;


import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.TypeMaterialRes;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
public interface ITypeMaterialService {

    Collection<TypeMaterialRes> findAllTypeCellar() throws NotFoundException;

    TypeMaterial findCellarById(Integer id) throws NotFoundException;

}
