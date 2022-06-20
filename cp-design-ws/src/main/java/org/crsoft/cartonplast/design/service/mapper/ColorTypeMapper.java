package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.ColorType;
import org.crsoft.cartonplast.design.vo.res.ColorTypeRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 17/06/2022
 */
@Mapper(componentModel = "spring")
public interface ColorTypeMapper {

    ColorTypeRes toColorTypeRes(ColorType colorType);

    @WithoutAuditField
    ColorType toColorType(ColorTypeRes colorTypeRes);

    List<ColorTypeRes> toColorTypeResList(List<ColorType> colorTypes);
}
