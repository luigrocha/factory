package org.crsoft.cartonplast.design.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.design.repository.ColorARepository;
import org.crsoft.cartonplast.design.service.IColorAService;
import org.crsoft.cartonplast.design.service.mapper.ColorAMapper;
import org.crsoft.cartonplast.design.vo.res.ColorARes;
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
public class ColorAService implements IColorAService {

    private static final String TABLE_NAME = "CATCOP";
    private final ColorARepository colorARepository;
    private final ColorAMapper colorAMapper;

    public ColorAService(
            ColorARepository colorARepository,
            ColorAMapper colorAMapper) {
        this.colorARepository = colorARepository;
        this.colorAMapper = colorAMapper;
    }

    @Override
    public Collection<ColorARes> findAllValidColors() throws NotFoundException {
        try {
            return this.colorAMapper.colorsAToColorsARes(
                    this.colorARepository.findAllByValidToIsNullOrderByNameAsc()
            );
        } catch (Exception e) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createColorA(ColorA color) throws InsertException {
        try {
            color.setId(color.getId().toUpperCase());
            this.colorARepository.save(color);
        } catch (Exception e) {
            log.error("Error to createColorA: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public ColorARes findColorAByCode(String code) throws NotFoundException {
        return this.colorAMapper.colorAtoColorARes(getColorAById(code));
    }

    @Override
    public void updateColorAByCode(String code, ColorA color) throws NotFoundException, UpdateException {
        ColorA colorAById = getColorAById(code);
        try {
            colorAById.setName(color.getName().toLowerCase());
            colorAById.setUpdatedAt(LocalDateTime.now());
            colorAById.setColorCode(color.getColorCode());
            colorAById.setUpdatedBy(color.getUpdatedBy());
            this.colorARepository.save(colorAById);
        } catch (Exception e) {
            log.error("Error to updateColorAByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deleteColorAByCode(String code, String userName) throws NotFoundException, UpdateException {
        ColorA color = getColorAById(code);
        try {
            color.setUpdatedBy(userName);
            color.setUpdatedAt(LocalDateTime.now());
            color.setValidTo(LocalDateTime.now());
            this.colorARepository.save(color);
        } catch (Exception e) {
            log.error("Error to deleteColorAByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
        }
    }

    @Override
    public ColorA getColorAById(String id) throws NotFoundException {
        Optional<ColorA> color = this.colorARepository.findByIdAndValidToIsNull(id);
        if (color.isPresent()) {
            return color.get();
        } else {
            log.info("Error to getColorAById {}", id);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
