package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.crsoft.cartonplast.design.repository.HomoPolymerRepository;
import org.crsoft.cartonplast.design.service.IHomoPolymerService;
import org.crsoft.cartonplast.design.service.mapper.HomoPolymerMapper;
import org.crsoft.cartonplast.vo.req.HomoPolymerReq;
import org.crsoft.cartonplast.vo.res.HomoPolymerRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 11/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class HomoPolymerService implements IHomoPolymerService {

    private final HomoPolymerRepository homoPolymerRepository;
    private final HomoPolymerMapper homoPolymerMapper;

    @Override
    public Collection<HomoPolymerRes> findAllValidHomopolymers() {
        return this.homoPolymerMapper.homoPolymersToHomoPolymersRes(
                homoPolymerRepository.findAllByValidToIsNullOrderByPercentageAsc()
        );
    }

    @Override
    @Transactional
    public HomoPolymerRes createHomopolymer(HomoPolymerReq homoPolymerReq) {
        boolean exists = homoPolymerRepository.existsByHpCodeAndIsActive(homoPolymerReq.getHpCode());
        if (exists) {
            log.error("Homopolymer with code {} already exists", homoPolymerReq.getHpCode());
            throw new BusinessException(BusinessExceptionReason.HOMOPOLYMER_ALREADY_EXISTS, homoPolymerReq.getHpCode());
        }

        HomoPolymer homoPolymer = homoPolymerMapper.homoPolymerReqToHomoPolymer(homoPolymerReq);

        return homoPolymerMapper.homoPolymerToHomoPolymerRes(
                homoPolymerRepository.save(homoPolymer)
        );
    }

    @Override
    public HomoPolymerRes findHomoPolymerByCode(Integer code) {
        return this.homoPolymerMapper.homoPolymerToHomoPolymerRes(getHomoPolymerByCode(code));
    }

    @Override
    @Transactional
    public HomoPolymerRes updateHomoPolymerByCode(Integer code, HomoPolymerReq homoPolymerReq) {
        HomoPolymer homoPolymerByCode = getHomoPolymerByCode(code);

        boolean existsAnotherWithSameCode = homoPolymerRepository.existsByHpCodeAndIsActive(
                homoPolymerReq.getHpCode()
        );

        if (existsAnotherWithSameCode) {
            log.error("Homopolymer with code {} already exists", homoPolymerReq.getHpCode());
            throw new BusinessException(BusinessExceptionReason.HOMOPOLYMER_ALREADY_EXISTS, homoPolymerReq.getHpCode());
        }

        homoPolymerByCode.setPercentage(homoPolymerReq.getPercentage());
        homoPolymerByCode.setHpCode(homoPolymerReq.getHpCode());

        return this.homoPolymerMapper.homoPolymerToHomoPolymerRes(
                homoPolymerRepository.save(homoPolymerByCode)
        );
    }

    @Override
    @Transactional
    public boolean deleteHomoPolymerByCode(Integer code) {
        return homoPolymerRepository.findById(code)
                .map(machine -> {
                    machine.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    private HomoPolymer getHomoPolymerByCode(Integer code) {
        Optional<HomoPolymer> homoPolymer = this.homoPolymerRepository.findByIdAndValidToIsNull(code);
        if (homoPolymer.isPresent()) {
            return homoPolymer.get();
        } else {
            log.info("Error to getHomoPolymerByCode {}", code);
            throw new BusinessException(BusinessExceptionReason.HOMOPOLYMER_NOT_FOUND, code);
        }
    }

}
