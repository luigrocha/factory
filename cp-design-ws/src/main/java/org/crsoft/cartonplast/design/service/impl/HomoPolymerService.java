package org.crsoft.cartonplast.design.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.crsoft.cartonplast.design.repository.HomoPolymerRepository;
import org.crsoft.cartonplast.design.service.IHomoPolymerService;
import org.crsoft.cartonplast.design.service.mapper.HomoPolymerMapper;
import org.crsoft.cartonplast.design.vo.res.HomoPolymerRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.*;

/**
 * @author lpillaga on 11/05/2022
 */
@Service
@Slf4j
public class HomoPolymerService implements IHomoPolymerService {

    private static final String TABLE_NAME = "CATHOM";
    private final HomoPolymerRepository homoPolymerRepository;
    private final HomoPolymerMapper homoPolymerMapper;

    public HomoPolymerService(
            HomoPolymerRepository homoPolymerRepository,
            HomoPolymerMapper homoPolymerMapper) {
        this.homoPolymerRepository = homoPolymerRepository;
        this.homoPolymerMapper = homoPolymerMapper;
    }

    @Override
    public Collection<HomoPolymerRes> findAllValidHomopolymers() throws NotFoundException {
        try {
            return this.homoPolymerMapper.homoPolymersToHomoPolymersRes(
                    homoPolymerRepository.findAllByValidToIsNullOrderByPercentageAsc()
            );
        } catch (Exception e) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createHomopolymer(HomoPolymer homoPolymer) throws InsertException {
        try {
            homoPolymer.setHpCode(homoPolymer.getHpCode().toUpperCase());
            this.homoPolymerRepository.save(homoPolymer);
        } catch (Exception e) {
            log.error("Error to createHomopolymer: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public HomoPolymerRes findHomoPolymerByCode(Integer code) throws NotFoundException {
        return this.homoPolymerMapper.homoPolymerToHomoPolymerRes(getHomoPolymerByCode(code));
    }

    @Override
    public void updateHomoPolymerByCode(Integer code, HomoPolymer homoPolymer) throws NotFoundException, UpdateException {
        HomoPolymer homoPolymerByCode = getHomoPolymerByCode(code);
        try {
            homoPolymerByCode.setPercentage(homoPolymer.getPercentage());
            homoPolymerByCode.setHpCode(homoPolymer.getHpCode());
            homoPolymerByCode.setUpdatedAt(LocalDateTime.now());
            homoPolymerByCode.setUpdatedBy(homoPolymer.getUpdatedBy());
            this.homoPolymerRepository.save(homoPolymerByCode);
        } catch (Exception e) {
            log.error("Error to updateHomoPolymerByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deleteHomoPolymerByCode(Integer code, String userName) throws NotFoundException, UpdateException {
        HomoPolymer homoPolymer = getHomoPolymerByCode(code);
        try {
            homoPolymer.setUpdatedBy(userName);
            homoPolymer.setUpdatedAt(LocalDateTime.now());
            homoPolymer.setValidTo(LocalDateTime.now());
            this.homoPolymerRepository.save(homoPolymer);
        } catch (Exception e) {
            log.error("Error to deleteHomoPolymerByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
        }
    }

    private HomoPolymer getHomoPolymerByCode(Integer code) throws NotFoundException {
        Optional<HomoPolymer> homoPolymer = this.homoPolymerRepository.findByIdAndValidToIsNull(code);
        if (homoPolymer.isPresent()) {
            return homoPolymer.get();
        } else {
            log.info("Error to getHomoPolymerByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

}
