package org.crsoft.cartonplast.common.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;

import java.util.Collection;

/**
 * @author jyepez on 26/5/2022
 */
public interface ICatalogStatusService {

    Collection<CatalogStatusRes> findAllStatusByType(String type) throws NotFoundException;
}
