package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.Ethnic;
import org.crsoft.cartonplast.users.vo.req.EthnicReq;
import org.crsoft.cartonplast.users.vo.res.EthnicRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Mapper(componentModel = "spring")
public interface EthnicMapper {

    EthnicRes ethnicToEthnicRes(Ethnic ethnic);

    @WithoutAuditField
    Ethnic ethnicResToEthnic(EthnicRes ethnicRes);

    @WithoutAuditField
    Ethnic ethnicReqToEthnic(EthnicReq ethnicReq);

    List<EthnicRes> ethnicListToEthnicResList(List<Ethnic> ethnics);
}
