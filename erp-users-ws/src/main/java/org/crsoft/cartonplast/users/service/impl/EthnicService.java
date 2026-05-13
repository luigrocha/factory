package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.ConflictException;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.Ethnic;
import org.crsoft.cartonplast.users.repository.EthnicRepository;
import org.crsoft.cartonplast.users.service.IEthnicService;
import org.crsoft.cartonplast.users.service.mapper.EthnicMapper;
import org.crsoft.cartonplast.users.vo.req.EthnicReq;
import org.crsoft.cartonplast.users.vo.res.EthnicRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 30/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class EthnicService implements IEthnicService {

    private final EthnicRepository ethnicRepository;
    private final EthnicMapper ethnicMapper;

    @Override
    public List<EthnicRes> findAllValidEthnics() {
        return ethnicMapper.ethnicListToEthnicResList(ethnicRepository.findAllValidEthnics());
    }

    @Override
    @Transactional
    public EthnicRes save(EthnicReq ethnicReq) {
        boolean alreadyExists = ethnicRepository.existsById(ethnicReq.getId());

        if (alreadyExists) {
            log.error(LogMessageConstant.ERROR_INSERT_MESSAGE + ethnicReq);
            throw new ConflictException(MessageConstant.MESSAGE_CONFLICT_INSERT);
        }

        Ethnic ethnic = ethnicMapper.ethnicReqToEthnic(ethnicReq);
        ethnic = ethnicRepository.save(ethnic);

        return ethnicMapper.ethnicToEthnicRes(ethnic);
    }

    @Override
    @Transactional
    public EthnicRes update(String id, EthnicReq ethnicReq) {
        Optional<Ethnic> ethnicOptional = ethnicRepository.findById(id);

        if (ethnicOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + ethnicReq);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        Ethnic ethnic = ethnicOptional.get();
        ethnic.setName(ethnicReq.getName());

        return ethnicMapper.ethnicToEthnicRes(ethnic);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return ethnicRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
