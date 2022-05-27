package org.crsoft.cartonplast.common.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CatalogCellarRes;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
public interface ICatalogCellarService {

    Collection<CatalogCellarRes> findAllCatalogCellarByType(Integer id) throws NotFoundException;

}
