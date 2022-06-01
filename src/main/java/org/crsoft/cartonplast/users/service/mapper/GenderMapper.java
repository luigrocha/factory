package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.Gender;
import org.crsoft.cartonplast.users.vo.req.GenderReq;
import org.crsoft.cartonplast.users.vo.res.GenderRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Mapper(componentModel = "spring")
public interface GenderMapper {

    GenderRes genderToGenderRes(Gender gender);

    @WithoutAuditField
    Gender genderResToGender(GenderRes genderRes);

    @WithoutAuditField
    Gender genderReqToGender(GenderReq genderReq);

    List<GenderRes> genderListToGenderResList(List<Gender> genders);
}
