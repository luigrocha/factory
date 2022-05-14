package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.ColorB;
import org.crsoft.cartonplast.design.vo.res.ColorBRes;
import org.crsoft.cartonplast.design.vo.res.ColorBShortRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring", uses = {ColorAMapper.class})
public interface ColorBMapper {

    ColorBRes colorBToColorBRes(ColorB colorB);

    ColorB colorBResToColorB(ColorBRes colorBRes);

    ColorBShortRes colorBToColorBShortRes(ColorB colorB);

    List<ColorBRes> colorsBToColorsBRes(List<ColorB> colorsB);
}
