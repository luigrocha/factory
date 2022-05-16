package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Thickness;
import org.crsoft.cartonplast.design.vo.res.ThicknessRes;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring")
public interface ThicknessMapper {

    @Mapping(source = "thick", target = "thickness")
    ThicknessRes thicknessToThicknessRes(Thickness thickness);

    @InheritInverseConfiguration
    @WithoutAuditField
    Thickness thicknessResToThickness(ThicknessRes thicknessRes);

    List<ThicknessRes> thicknessesToThicknessesRes(List<Thickness> thicknesses);
}
