package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.SecondDigitReq;
import org.crsoft.cartonplast.users.vo.res.SecondDigitRes;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
public interface ISecondDigitService {

    List<SecondDigitRes> findAllValidSecondDigits();

    SecondDigitRes save(SecondDigitReq secondDigitReq);

    SecondDigitRes update(String id, SecondDigitReq secondDigitReq);

    boolean delete(String id);
}
