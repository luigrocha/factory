package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.EthnicReq;
import org.crsoft.cartonplast.users.vo.res.EthnicRes;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
public interface IEthnicService {

    List<EthnicRes> findAllValidEthnics();
    EthnicRes save(EthnicReq ethnicReq);
    EthnicRes update(String id, EthnicReq ethnicReq);
    boolean delete(String id);
}
