package org.crsoft.cartonplast.common.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 26/5/2022
 */
public interface ICatalogStatusService {

    Collection<CatalogStatusRes> findAllStatusByType(String type) throws NotFoundException;

    Optional<CatalogStatus> findByTypeAndIsDefault(String type);
    Optional<CatalogStatus> findById(String id);
}
