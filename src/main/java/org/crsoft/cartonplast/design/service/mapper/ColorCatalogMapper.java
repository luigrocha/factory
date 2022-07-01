package org.crsoft.cartonplast.design.service.mapper;


import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.design.model.ColorCatalog;
import org.crsoft.cartonplast.vo.res.ColorCatalogRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

/**
 * @author lpillaga on 14/05/2022
 */
@Mapper(componentModel = "spring")
public interface ColorCatalogMapper {

    @WithoutAuditField
    @Mapping(target = "cyrelColors", ignore = true)
    ColorCatalog colorCatalogResToColorCatalog(ColorCatalogRes colorCatalogRes);

    ColorCatalogRes colorCatalogToColorCatalogRes(ColorCatalog colorCatalog);

    Collection<ColorCatalogRes> colorCatalogListToColorCatalogResList(Collection<ColorCatalog> colorCatalogList);
}
