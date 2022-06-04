package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.MaterialRes;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
public interface IMaterialService {

    Collection<MaterialRes> findAllCatalogCellarByType(Integer id) throws NotFoundException;

    Material getMaterialByCode(Integer code) throws NotFoundException;

}
