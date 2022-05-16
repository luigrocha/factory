package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.repository.ColorBRepository;
import org.crsoft.cartonplast.design.service.IColorBService;
import org.crsoft.cartonplast.design.service.mapper.ColorBMapper;
import org.crsoft.cartonplast.design.vo.res.ColorBRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Service
public class ColorBService implements IColorBService {

    private final ColorBRepository colorBRepository;
    private final ColorBMapper colorBMapper;

    public ColorBService(
            ColorBRepository colorBRepository,
            ColorBMapper colorBMapper) {
        this.colorBRepository = colorBRepository;
        this.colorBMapper = colorBMapper;
    }

    @Override
    public List<ColorBRes> findAllValidColors() {
        return this.colorBMapper.colorsBToColorsBRes(
                this.colorBRepository.findAllValidColors()
        );
    }
}
