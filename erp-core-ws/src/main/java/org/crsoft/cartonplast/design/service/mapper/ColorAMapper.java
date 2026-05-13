package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.vo.req.ColorAReq;
import org.crsoft.cartonplast.vo.res.ColorARes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring")
public interface ColorAMapper {

    ColorARes colorAtoColorARes(ColorA colorA);

    @WithoutAuditField
    @Mapping(target = "colorsB", ignore = true)
    ColorA colorAResToColorA(ColorARes colorARes);

    @WithoutAuditField
    ColorA colorReqToColorA(ColorAReq colorAReq);

    Collection<ColorARes> colorsAToColorsARes(Collection<ColorA> colorsA);
}
