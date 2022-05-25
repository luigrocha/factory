package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.ColorB;
import org.crsoft.cartonplast.design.vo.res.ColorBRes;
import org.crsoft.cartonplast.design.vo.res.ColorBShortRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring", uses = {ColorAMapper.class})
public interface ColorBMapper {

    ColorBRes colorBToColorBRes(ColorB colorB);

    @WithoutAuditField
    @Mapping(target = "cyrels", ignore = true)
    ColorB colorBResToColorB(ColorBRes colorBRes);

    ColorBShortRes colorBToColorBShortRes(ColorB colorB);

    Collection<ColorBRes> colorsBToColorsBRes(Collection<ColorB> colorsB);
}
