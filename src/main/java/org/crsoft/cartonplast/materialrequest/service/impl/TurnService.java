package org.crsoft.cartonplast.materialrequest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.materialrequest.model.Turn;
import org.crsoft.cartonplast.materialrequest.repository.TurnRepository;
import org.crsoft.cartonplast.materialrequest.service.ITurnService;
import org.crsoft.cartonplast.materialrequest.service.mapper.TurnMapper;
import org.crsoft.cartonplast.vo.req.TurnReq;
import org.crsoft.cartonplast.vo.res.TurnRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class TurnService implements ITurnService {

    private final TurnRepository turnRepository;
    private final TurnMapper turnMapper;

    @Override
    public List<TurnRes> findAllValidTurns(Boolean isActive) {
        return turnMapper.turnsToTurnsRes(turnRepository.findAllValidTurns(isActive));
    }

    @Override
    @Transactional
    public TurnRes updateTurn(Integer id, TurnReq turnReq) {
        Turn turn = this.findTurnById(id);

        turn.setName(turnReq.getName());
        turn.setStartTime(turnReq.getStartTime());
        turn.setEndTime(turnReq.getEndTime());
        turn.setIsActive(turnReq.getIsActive());

        return this.turnMapper.turnToTurnRes(turn);
    }

    @Override
    public Turn findTurnById(Integer id) {
        return this.turnRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Turn not found with id: {}", id);
                    throw new BusinessException(BusinessExceptionReason.TURN_NOT_FOUND, id);
                });
    }
}
