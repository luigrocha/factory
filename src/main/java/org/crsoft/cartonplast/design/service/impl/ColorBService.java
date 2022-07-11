package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.design.model.ColorB;
import org.crsoft.cartonplast.design.repository.ColorBRepository;
import org.crsoft.cartonplast.design.service.IColorBService;
import org.crsoft.cartonplast.design.service.mapper.ColorBMapper;
import org.crsoft.cartonplast.vo.req.ColorBReq;
import org.crsoft.cartonplast.vo.res.ColorBRes;
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
public class ColorBService implements IColorBService {

    private final ColorBRepository colorBRepository;
    private final ColorBMapper colorBMapper;
    private final ColorAService colorAService;

    @Override
    public Collection<ColorBRes> findAllValidColors() {
        return this.colorBMapper.colorsBToColorsBRes(
                this.colorBRepository.findAllByValidToIsNullOrderByIdAsc()
        );
    }

    @Override
    @Transactional
    public ColorBRes createColorB(ColorBReq colorBReq) {
        boolean exists = this.colorBRepository.existsByCodeAndIsNotDeleted(colorBReq.getId());
        if (exists) {
            log.error("ColorB with id {} already exists", colorBReq.getId());
            throw new BusinessException(BusinessExceptionReason.COLOR_B_ALREADY_EXISTS, colorBReq.getId());
        }

        ColorB colorB = this.colorBMapper.colorBReqToColorB(colorBReq);
        ColorA colorA = this.colorAService.getColorAById(colorBReq.getColorAId());
        colorB.setColorA(colorA);

        return this.colorBMapper.colorBToColorBRes(
                this.colorBRepository.save(colorB)
        );
    }

    @Override
    public ColorBRes findColorBByCode(String code) {
        ColorB colorB = this.getColorBById(code);
        return this.colorBMapper.colorBToColorBRes(colorB);
    }

    @Override
    @Transactional
    public ColorBRes updateColorBByCode(String code, ColorBReq colorBReq) {
        ColorB colorB = this.getColorBById(code);
        ColorA colorA = this.colorAService.getColorAById(colorBReq.getColorAId());
        colorB.setColorA(colorA);
        colorB.setIndex(colorBReq.getIndex());
        colorB.setDosage(colorBReq.getDosage());
        colorB.setColorCode(colorBReq.getColorCode());
        colorB.setDescription(colorBReq.getDescription());
        colorB.setObservation(colorBReq.getObservation());

        return this.colorBMapper.colorBToColorBRes(colorB);
    }

    @Override
    @Transactional
    public boolean deleteColorBByCode(String code) {
        return colorBRepository.findById(code)
                .map(machine -> {
                    machine.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public ColorB getColorBById(String code) {
        Optional<ColorB> colorB = this.colorBRepository.findByIdAndValidToIsNull(code);
        if (colorB.isPresent()) {
            return colorB.get();
        } else {
            log.error("ColorB not found with code: {}", code);
            throw new BusinessException(BusinessExceptionReason.COLOR_B_NOT_FOUND, code);
        }
    }
}
