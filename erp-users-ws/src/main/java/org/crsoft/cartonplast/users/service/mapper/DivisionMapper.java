package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.Division;
import org.crsoft.cartonplast.users.vo.req.DivisionReq;
import org.crsoft.cartonplast.users.vo.res.DivisionRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Mapper(componentModel = "spring")
public interface DivisionMapper {

    DivisionRes toDivisionRes(Division division);

    @WithoutAuditField
    Division toDivision(DivisionReq divisionReq);

    @WithoutAuditField
    Division toDivision(DivisionRes divisionRes);

    List<DivisionRes> toDivisionResList(List<Division> divisions);
}
