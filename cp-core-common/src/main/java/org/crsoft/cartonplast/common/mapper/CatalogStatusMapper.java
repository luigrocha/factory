package org.crsoft.cartonplast.common.mapper;

import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;
import org.mapstruct.Mapper;

/**
 * @author lpillaga on 14/05/2022
 */
@Mapper(componentModel = "spring")
public interface CatalogStatusMapper {

    CatalogStatusRes dieStatusToDieStatusRes(CatalogStatus dieStatus);
}
