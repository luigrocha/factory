package org.crsoft.cartonplast.catalog.service;

import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

/**
 * @author jyepez on 26/5/2022
 */
public interface ICatalogStatusService {

    Collection<CatalogStatusRes> findAllStatusByType(String type);

    Optional<CatalogStatus> findByTypeAndIsDefault(String type);

    Optional<CatalogStatus> findById(String id);

    List<CatalogStatus> findByIds(List<String> ids);
}
