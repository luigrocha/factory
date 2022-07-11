package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.design.repository.ColorARepository;
import org.crsoft.cartonplast.design.service.IColorAService;
import org.crsoft.cartonplast.design.service.mapper.ColorAMapper;
import org.crsoft.cartonplast.vo.req.ColorAReq;
import org.crsoft.cartonplast.vo.res.ColorARes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ColorAService implements IColorAService {

    private final ColorARepository colorARepository;
    private final ColorAMapper colorAMapper;

    @Override
    public Collection<ColorARes> findAllValidColors() {
        return this.colorAMapper.colorsAToColorsARes(
                this.colorARepository.findAllByValidToIsNullOrderByNameAsc()
        );
    }

    @Override
    @Transactional
    public ColorARes createColorA(ColorAReq colorAReq) {
        boolean exists = this.colorARepository.existsByCodeAndIsNotDeleted(colorAReq.getId());
        if (exists) {
            log.error("ColorA with id {} already exists", colorAReq.getId());
            throw new BusinessException(BusinessExceptionReason.COLOR_A_ALREADY_EXISTS, colorAReq.getId());
        }

        ColorA colorA = this.colorAMapper.colorReqToColorA(colorAReq);
        return this.colorAMapper.colorAtoColorARes(
                this.colorARepository.save(colorA)
        );
    }

    @Override
    public ColorARes findColorAByCode(String code) {
        ColorA colorA = this.getColorAById(code);
        return this.colorAMapper.colorAtoColorARes(colorA);
    }

    @Override
    @Transactional
    public ColorARes updateColorAByCode(String code, ColorAReq color) {
        ColorA colorA = this.getColorAById(code);
        colorA.setName(color.getName());
        colorA.setColorCode(color.getColorCode());

        return this.colorAMapper.colorAtoColorARes(colorA);
    }

    @Override
    @Transactional
    public boolean deleteColorAByCode(String code) {
        return colorARepository.findById(code)
                .map(machine -> {
                    machine.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public ColorA getColorAById(String id) {
        return this.colorARepository.findByIdAndValidToIsNull(id)
                .orElseThrow(() -> {
                    log.error("ColorA not found with code: {}", id);
                    throw new BusinessException(BusinessExceptionReason.COLOR_A_NOT_FOUND, id);
                });
    }
}
