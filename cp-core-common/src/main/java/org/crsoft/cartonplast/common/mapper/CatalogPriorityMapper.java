package org.crsoft.cartonplast.common.mapper;

import org.crsoft.cartonplast.common.model.CatalogPriority;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;
import org.mapstruct.Mapper;

/**
 * @author jyepez on 14/5/2022
 */
@Mapper(componentModel = "spring")
public interface CatalogPriorityMapper {

    CatalogPriorityRes catalogPriorityToCatalogRes(CatalogPriority catalogPriority);
}
