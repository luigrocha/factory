package org.crsoft.cartonplast.catalog.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;

import java.util.Collection;

/**
 * @author jyepez on 25/5/2022
 */
public interface ICatalogPriorityService {

    Collection<CatalogPriorityRes> findAllPriorityByType(String type) throws NotFoundException;

}
