package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.design.vo.res.ColorARes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring")
public interface ColorAMapper {

    ColorARes colorAtoColorARes(ColorA colorA);

    ColorA colorAResToColorA(ColorARes colorARes);

    List<ColorARes> colorsAToColorsARes(List<ColorA> colorsA);
}
