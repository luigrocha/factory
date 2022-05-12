package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.repository.ColorARepository;
import org.crsoft.cartonplast.design.service.IColorAService;
import org.crsoft.cartonplast.design.service.mapper.ColorAMapper;
import org.crsoft.cartonplast.design.vo.res.ColorARes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Service
public class ColorAService implements IColorAService {

    private final ColorARepository colorARepository;
    private final ColorAMapper colorAMapper;

    public ColorAService(
            ColorARepository colorARepository,
            ColorAMapper colorAMapper) {
        this.colorARepository = colorARepository;
        this.colorAMapper = colorAMapper;
    }

    @Override
    public List<ColorARes> findAllValidColors() {
        return this.colorAMapper.colorsAToColorsARes(
                this.colorARepository.findAllValidColors()
        );
    }
}
