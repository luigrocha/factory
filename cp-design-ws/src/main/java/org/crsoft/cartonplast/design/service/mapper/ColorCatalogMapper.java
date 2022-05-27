package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.ColorCatalog;
import org.crsoft.cartonplast.design.vo.res.ColorCatalogRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 14/05/2022
 */
@Mapper(componentModel = "spring")
public interface ColorCatalogMapper {

    @WithoutAuditField
    @Mapping(target = "cyrelColors", ignore = true)
    ColorCatalog colorCatalogResToColorCatalog(ColorCatalogRes colorCatalogRes);

    ColorCatalogRes colorCatalogToColorCatalogRes(ColorCatalog colorCatalog);

    List<ColorCatalogRes> colorCatalogListToColorCatalogResList(List<ColorCatalog> colorCatalogList);
}
