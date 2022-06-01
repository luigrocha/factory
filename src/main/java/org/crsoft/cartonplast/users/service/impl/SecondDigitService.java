package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.ConflictException;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.SecondDigit;
import org.crsoft.cartonplast.users.repository.SecondDigitRepository;
import org.crsoft.cartonplast.users.service.ISecondDigitService;
import org.crsoft.cartonplast.users.service.mapper.SecondDigitMapper;
import org.crsoft.cartonplast.users.vo.req.SecondDigitReq;
import org.crsoft.cartonplast.users.vo.res.SecondDigitRes;
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
public class SecondDigitService implements ISecondDigitService {

    private final SecondDigitRepository secondDigitRepository;
    private final SecondDigitMapper secondDigitMapper;

    @Override
    public List<SecondDigitRes> findAllValidSecondDigits() {
        return secondDigitMapper.toSecondDigitResList(secondDigitRepository.findAllValidSecondDigits());
    }

    @Override
    @Transactional
    public SecondDigitRes save(SecondDigitReq secondDigitReq) {
        boolean alreadyExists = secondDigitRepository.existsById(secondDigitReq.getId());

        if (alreadyExists) {
            log.error(LogMessageConstant.ERROR_INSERT_MESSAGE + secondDigitReq);
            throw new ConflictException(MessageConstant.MESSAGE_CONFLICT_INSERT);
        }

        SecondDigit secondDigit = secondDigitMapper.toSecondDigit(secondDigitReq);
        secondDigit = secondDigitRepository.save(secondDigit);

        return secondDigitMapper.toSecondDigitRes(secondDigit);
    }

    @Override
    @Transactional
    public SecondDigitRes update(String id, SecondDigitReq secondDigitReq) {
        Optional<SecondDigit> secondDigitOptional = secondDigitRepository.findById(id);

        if (secondDigitOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + secondDigitReq);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        SecondDigit secondDigit = secondDigitMapper.toSecondDigit(secondDigitReq);
        secondDigit = secondDigitRepository.save(secondDigit);

        return secondDigitMapper.toSecondDigitRes(secondDigit);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return secondDigitRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
