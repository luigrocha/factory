package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.design.vo.res.ColorARes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring")
public interface ColorAMapper {

    ColorARes colorAtoColorARes(ColorA colorA);

    @WithoutAuditField
    ColorA colorAResToColorA(ColorARes colorARes);

    Collection<ColorARes> colorsAToColorsARes(Collection<ColorA> colorsA);
}
