package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.SecondDigit;
import org.crsoft.cartonplast.users.vo.req.SecondDigitReq;
import org.crsoft.cartonplast.users.vo.res.SecondDigitRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Mapper(componentModel = "spring")
public interface SecondDigitMapper {

    SecondDigitRes toSecondDigitRes(SecondDigit secondDigit);

    @WithoutAuditField
    SecondDigit toSecondDigit(SecondDigitRes secondDigitRes);

    @WithoutAuditField
    SecondDigit toSecondDigit(SecondDigitReq secondDigitReq);

    List<SecondDigitRes> toSecondDigitResList(List<SecondDigit> secondDigitList);
}
