package org.crsoft.cartonplast.catalog.service;

import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;

import java.util.List;

/**
 * @author jyepez on 25/5/2022
 */
public interface ICatalogPriorityService {

    List<CatalogPriorityRes> findAllPriorityByType(String type);

    CatalogPriority findPriorityById(String id);
}
