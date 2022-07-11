package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Thickness;
import org.crsoft.cartonplast.vo.req.ThicknessReq;
import org.crsoft.cartonplast.vo.res.ThicknessRes;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring")
public interface ThicknessMapper {

    ThicknessRes thicknessToThicknessRes(Thickness thickness);

    @InheritInverseConfiguration
    @WithoutAuditField
    Thickness thicknessResToThickness(ThicknessRes thicknessRes);

    @WithoutAuditField
    Thickness thicknessReqToThickness(ThicknessReq thicknessReq);

    Collection<ThicknessRes> thicknessesToThicknessesRes(Collection<Thickness> thicknesses);
}
