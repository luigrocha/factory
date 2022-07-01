package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.repository.ColorTypeRepository;
import org.crsoft.cartonplast.design.service.IColorType;
import org.crsoft.cartonplast.design.service.mapper.ColorTypeMapper;
import org.crsoft.cartonplast.vo.res.ColorTypeRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 17/06/2022
 */
@Service
@RequiredArgsConstructor
public class ColorTypeService implements IColorType {

    private final ColorTypeRepository colorTypeRepository;
    private final ColorTypeMapper colorTypeMapper;

    @Override
    public List<ColorTypeRes> findAllValidColorTypes() {
        return colorTypeMapper.toColorTypeResList(colorTypeRepository.findAllValidColorTypes());
    }
}
