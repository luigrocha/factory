package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.ConflictException;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.Gender;
import org.crsoft.cartonplast.users.repository.GenderRepository;
import org.crsoft.cartonplast.users.service.IGenderService;
import org.crsoft.cartonplast.users.service.mapper.GenderMapper;
import org.crsoft.cartonplast.users.vo.req.GenderReq;
import org.crsoft.cartonplast.users.vo.res.GenderRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 30/05/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class GenderService implements IGenderService {

    private final GenderRepository genderRepository;
    private final GenderMapper genderMapper;

    @Override
    public List<GenderRes> findAllValidGenders() {
        return genderMapper.genderListToGenderResList(genderRepository.findAllValidGenders());
    }

    @Override
    @Transactional
    public GenderRes save(GenderReq genderReq) {
        boolean alreadyExists = genderRepository.existsById(genderReq.getId());

        if (alreadyExists) {
            log.error(LogMessageConstant.ERROR_INSERT_MESSAGE + genderReq);
            throw new ConflictException(MessageConstant.MESSAGE_CONFLICT_INSERT);
        }

        Gender gender = genderMapper.genderReqToGender(genderReq);
        gender = genderRepository.save(gender);

        return genderMapper.genderToGenderRes(gender);
    }

    @Override
    @Transactional
    public GenderRes update(String id, GenderReq genderReq) {
        Optional<Gender> genderOptional = genderRepository.findById(id);

        if (genderOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + genderReq);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        Gender gender = genderOptional.get();
        gender.setName(genderReq.getName());

        return genderMapper.genderToGenderRes(gender);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return genderRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
