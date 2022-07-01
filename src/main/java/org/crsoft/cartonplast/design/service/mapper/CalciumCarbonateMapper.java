package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.CalciumCarbonate;
import org.crsoft.cartonplast.vo.res.CalciumCarbonateRes;
import org.mapstruct.Mapper;

/**
 * @author lpillaga on 23/06/2022
 */
@Mapper(componentModel = "spring")
public interface CalciumCarbonateMapper {

    CalciumCarbonateRes toCalciumCarbonateRes(CalciumCarbonate calciumCarbonate);
}
