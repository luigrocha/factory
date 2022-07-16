package org.crsoft.cartonplast.materialrequest.service;

import org.crsoft.cartonplast.materialrequest.model.Turn;
import org.crsoft.cartonplast.vo.req.TurnReq;
import org.crsoft.cartonplast.vo.res.TurnRes;

import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
public interface ITurnService {

    List<TurnRes> findAllValidTurns(Boolean isActive);

    TurnRes updateTurn(Integer id, TurnReq turnReq);

    Turn findTurnById(Integer id);

}
