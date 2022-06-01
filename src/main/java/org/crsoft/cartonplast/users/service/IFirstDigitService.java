package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.FirstDigitReq;
import org.crsoft.cartonplast.users.vo.res.FirstDigitRes;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
public interface IFirstDigitService {

    List<FirstDigitRes> findAllValidFirstDigits();

    FirstDigitRes save(FirstDigitReq firstDigitReq);

    FirstDigitRes update(String id, FirstDigitReq firstDigitReq);

    boolean delete(String id);
}
