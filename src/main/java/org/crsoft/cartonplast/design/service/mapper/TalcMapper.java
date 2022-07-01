package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.Talc;
import org.crsoft.cartonplast.vo.res.TalcRes;
import org.mapstruct.Mapper;

/**
 * @author lpillaga on 23/06/2022
 */
@Mapper(componentModel = "spring")
public interface TalcMapper {

    TalcRes toTalcRes(Talc talc);
}
