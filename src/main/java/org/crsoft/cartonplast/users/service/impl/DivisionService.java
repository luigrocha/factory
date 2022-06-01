package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.ConflictException;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.Division;
import org.crsoft.cartonplast.users.repository.DivisionRepository;
import org.crsoft.cartonplast.users.service.IDivisionService;
import org.crsoft.cartonplast.users.service.mapper.DivisionMapper;
import org.crsoft.cartonplast.users.vo.req.DivisionReq;
import org.crsoft.cartonplast.users.vo.res.DivisionRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 31/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class DivisionService implements IDivisionService {

    private final DivisionRepository divisionRepository;
    private final DivisionMapper divisionMapper;

    @Override
    public List<DivisionRes> findAllValidDivisions() {
        return divisionMapper.toDivisionResList(divisionRepository.findAllValidDivisions());
    }

    @Override
    @Transactional
    public DivisionRes save(DivisionReq divisionReq) {
        boolean alreadyExists = divisionRepository.existsById(divisionReq.getId());

        if (alreadyExists) {
            log.error(LogMessageConstant.ERROR_INSERT_MESSAGE + divisionReq);
            throw new ConflictException(MessageConstant.MESSAGE_CONFLICT_INSERT);
        }

        Division division = divisionMapper.toDivision(divisionReq);
        division = divisionRepository.save(division);

        return divisionMapper.toDivisionRes(division);
    }

    @Override
    @Transactional
    public DivisionRes update(String id, DivisionReq divisionReq) {
        Optional<Division> divisionOptional = divisionRepository.findById(id);

        if (divisionOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + divisionReq);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        Division division = divisionMapper.toDivision(divisionReq);
        division = divisionRepository.save(division);

        return divisionMapper.toDivisionRes(division);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return divisionRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
