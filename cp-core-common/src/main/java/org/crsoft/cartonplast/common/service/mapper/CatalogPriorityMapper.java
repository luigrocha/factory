package org.crsoft.cartonplast.common.service.mapper;

import org.crsoft.cartonplast.common.model.CatalogPriority;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 14/5/2022
 */
@Mapper(componentModel = "spring")
public interface CatalogPriorityMapper {

    CatalogPriorityRes catalogPriorityToCatalogRes(CatalogPriority catalogPriority);

    Collection<CatalogPriorityRes> catalogPriorityCollectionToCatalogResCollection(Collection<CatalogPriority> catalogPriorities);
}
