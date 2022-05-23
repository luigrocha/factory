package org.crsoft.cartonplast.design.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorB;
import org.crsoft.cartonplast.design.repository.ColorBRepository;
import org.crsoft.cartonplast.design.service.IColorBService;
import org.crsoft.cartonplast.design.service.mapper.ColorBMapper;
import org.crsoft.cartonplast.design.vo.res.ColorBRes;
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
public class ColorBService implements IColorBService {

    private static final String TABLE_NAME = "CATCOL";
    private final ColorBRepository colorBRepository;
    private final ColorBMapper colorBMapper;

    public ColorBService(
            ColorBRepository colorBRepository,
            ColorBMapper colorBMapper) {
        this.colorBRepository = colorBRepository;
        this.colorBMapper = colorBMapper;
    }

    @Override
    public Collection<ColorBRes> findAllValidColors() throws NotFoundException {
        try {
            return this.colorBMapper.colorsBToColorsBRes(
                    this.colorBRepository.findAllByValidToIsNullOrderByIndexAsc()
            );
        } catch (Exception e) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createColorB(ColorB colorB) throws InsertException {
        try {
            this.colorBRepository.save(colorB);
        } catch (Exception e) {
            log.error("Error to createColorB: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }

    }

    @Override
    public ColorBRes findColorBByCode(String code) throws NotFoundException {
        return this.colorBMapper.colorBToColorBRes(getColorBByCode(code));
    }

    @Override
    public void updateColorBByCode(String code, ColorB colorB) throws NotFoundException, UpdateException {
        ColorB color = getColorBByCode(code);
        try {
            color.setIndex(colorB.getIndex());
            color.setDosage(colorB.getDosage());
            color.setDescription(colorB.getDescription());
            color.setUpdatedAt(LocalDateTime.now());
            this.colorBRepository.save(color);
        } catch (Exception e) {
            log.error("Error to updateColorBByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deleteColorBByCode(String code, String userName) throws NotFoundException, UpdateException {
        ColorB color = getColorBByCode(code);
        try {
            color.setUpdatedBy(userName);
            color.setUpdatedAt(LocalDateTime.now());
            color.setValidTo(LocalDateTime.now());
            this.colorBRepository.save(color);
        } catch (Exception e) {
            log.error("Error to deleteHomoPolymerByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
        }
    }

    private ColorB getColorBByCode(String code) throws NotFoundException {
        Optional<ColorB> colorB = this.colorBRepository.findByIdAndValidToIsNull(code);
        if (colorB.isPresent()) {
            return colorB.get();
        } else {
            log.info("Error to getColorBByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

}
