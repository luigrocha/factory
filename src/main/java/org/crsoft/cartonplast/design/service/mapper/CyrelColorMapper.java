package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.design.model.CyrelColor;
import org.crsoft.cartonplast.vo.req.CyrelColorReq;
import org.crsoft.cartonplast.vo.res.CyrelColorRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author lpillaga on 15/05/2022
 */
@Mapper(componentModel = "spring")
public interface CyrelColorMapper {

    @Mapping(target = "color", source = "color.name")
    @Mapping(target = "colorType", source = "colorType.name")
    @Mapping(target = "colorCode", source = "color.colorCode")
    CyrelColorRes cyrelColorToCyrelColorRes(CyrelColor cyrelColor);

    @WithoutAuditField
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "colorType", ignore = true)
    @Mapping(target = "cyrel", ignore = true)
    CyrelColor cyrelColorResToCyrelColor(CyrelColorRes cyrelColorRes);

    @WithoutAuditField
    @Mapping(target = "color", ignore = true)
    @Mapping(target = "colorType", ignore = true)
    @Mapping(target = "cyrel", ignore = true)
    @Mapping(target = "id", ignore = true)
    CyrelColor cyrelColorReqToCyrelColor(CyrelColorReq cyrelColorReq);
}
