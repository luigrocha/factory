package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.ConflictException;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.FirstDigit;
import org.crsoft.cartonplast.users.repository.FirstDigitRepository;
import org.crsoft.cartonplast.users.service.IFirstDigitService;
import org.crsoft.cartonplast.users.service.mapper.FirstDigitMapper;
import org.crsoft.cartonplast.users.vo.req.FirstDigitReq;
import org.crsoft.cartonplast.users.vo.res.FirstDigitRes;
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
public class FirstDigitService implements IFirstDigitService {

    private final FirstDigitRepository firstDigitRepository;
    private final FirstDigitMapper firstDigitMapper;

    @Override
    public List<FirstDigitRes> findAllValidFirstDigits() {
        return firstDigitMapper.toFirstDigitResList(firstDigitRepository.findAllValidFirstDigits());
    }

    @Override
    @Transactional
    public FirstDigitRes save(FirstDigitReq firstDigitReq) {
        boolean alreadyExists = firstDigitRepository.existsById(firstDigitReq.getId());

        if (alreadyExists) {
            log.error(LogMessageConstant.ERROR_INSERT_MESSAGE + firstDigitReq);
            throw new ConflictException(MessageConstant.MESSAGE_CONFLICT_INSERT);
        }

        FirstDigit firstDigit = firstDigitMapper.toFirstDigit(firstDigitReq);
        firstDigit = firstDigitRepository.save(firstDigit);

        return firstDigitMapper.toFirstDigitRes(firstDigit);
    }

    @Override
    @Transactional
    public FirstDigitRes update(String id, FirstDigitReq firstDigitReq) {
        Optional<FirstDigit> firstDigitOptional = firstDigitRepository.findById(id);

        if (firstDigitOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + firstDigitReq);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        FirstDigit firstDigit = firstDigitOptional.get();
        firstDigit.setName(firstDigitReq.getName());

        return firstDigitMapper.toFirstDigitRes(firstDigit);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return firstDigitRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
