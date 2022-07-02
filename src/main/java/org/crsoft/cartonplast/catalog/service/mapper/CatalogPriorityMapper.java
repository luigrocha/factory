package org.crsoft.cartonplast.catalog.service.mapper;

import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;
import org.mapstruct.Mapper;

import java.util.Collection;
import java.util.List;

/**
 * @author jyepez on 14/5/2022
 */
@Mapper(componentModel = "spring")
public interface CatalogPriorityMapper {

    CatalogPriorityRes catalogPriorityToCatalogRes(CatalogPriority catalogPriority);

    List<CatalogPriorityRes> catalogPriorityCollectionToCatalogResCollection(List<CatalogPriority> catalogPriorities);
}
