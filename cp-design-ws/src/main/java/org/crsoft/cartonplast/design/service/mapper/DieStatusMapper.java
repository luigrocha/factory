package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.DieStatus;
import org.crsoft.cartonplast.design.vo.res.DieStatusRes;
import org.mapstruct.Mapper;

/**
 * @author lpillaga on 14/05/2022
 */
@Mapper(componentModel = "spring")
public interface DieStatusMapper {

    DieStatusRes dieStatusToDieStatusRes(DieStatus dieStatus);
}
