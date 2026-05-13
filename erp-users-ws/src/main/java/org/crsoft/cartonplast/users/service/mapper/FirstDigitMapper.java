package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.FirstDigit;
import org.crsoft.cartonplast.users.vo.req.FirstDigitReq;
import org.crsoft.cartonplast.users.vo.res.FirstDigitRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Mapper(componentModel = "spring")
public interface FirstDigitMapper {

    FirstDigitRes toFirstDigitRes(FirstDigit firstDigit);

    FirstDigit toFirstDigit(FirstDigitRes firstDigitRes);

    FirstDigit toFirstDigit(FirstDigitReq firstDigitReq);

    List<FirstDigitRes> toFirstDigitResList(List<FirstDigit> firstDigitList);
}
