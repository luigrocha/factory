package org.crsoft.cartonplast.materialrequest.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.materialrequest.model.Turn;
import org.crsoft.cartonplast.vo.req.TurnReq;
import org.crsoft.cartonplast.vo.res.TurnRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
@Mapper(componentModel = "spring")
public interface TurnMapper {

    TurnRes turnToTurnRes(Turn turn);

    @WithoutAuditField
    Turn turnReqToTurn(TurnReq turnReq);

    List<TurnRes> turnsToTurnsRes(List<Turn> turns);

}
