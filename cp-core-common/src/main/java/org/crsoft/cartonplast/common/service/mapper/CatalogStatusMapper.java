package org.crsoft.cartonplast.common.service.mapper;

import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author lpillaga on 14/05/2022
 */
@Mapper(componentModel = "spring")
public interface CatalogStatusMapper {

    @WithoutAuditField
    CatalogStatus toCatalogStatus(CatalogStatusRes catalogStatusRes);

    CatalogStatusRes dieStatusToDieStatusRes(CatalogStatus dieStatus);

    Collection<CatalogStatusRes> dieStatusCollectionToDieStatusResCollection(Collection<CatalogStatus> catalogStatuses);

}
