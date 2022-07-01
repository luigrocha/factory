package org.crsoft.cartonplast.design.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorCatalog;
import org.crsoft.cartonplast.design.repository.ColorCatalogRepository;
import org.crsoft.cartonplast.design.service.IColorCatalogService;
import org.crsoft.cartonplast.design.service.mapper.ColorCatalogMapper;
import org.crsoft.cartonplast.vo.res.ColorCatalogRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.*;

/**
 * @author jyepez on 30/5/2022
 */
@Service
@Slf4j
public class ColorCatalogService implements IColorCatalogService {

    private static final String TABLE_NAME = "CATCATCOL";
    private final ColorCatalogRepository colorCatalogRepository;
    private final ColorCatalogMapper colorCatalogMapper;

    public ColorCatalogService(ColorCatalogRepository colorCatalogRepository, ColorCatalogMapper colorCatalogMapper) {
        this.colorCatalogRepository = colorCatalogRepository;
        this.colorCatalogMapper = colorCatalogMapper;
    }

    @Override
    public Collection<ColorCatalogRes> findAllValidColors() throws NotFoundException {
        try {
            return this.colorCatalogMapper.colorCatalogListToColorCatalogResList(
                    this.colorCatalogRepository.findAllByValidToIsNull()
            );
        } catch (Exception e) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createColorCatalog(ColorCatalog colorCatalog) throws InsertException {
        try {
            this.colorCatalogRepository.save(colorCatalog);
        } catch (Exception e) {
            log.error("Error to createColorCatalog: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public ColorCatalogRes findColorCatalogByCode(Integer code) throws NotFoundException {
        return this.colorCatalogMapper.colorCatalogToColorCatalogRes(getColorCatalogByCode(code));
    }

    @Override
    public void updateColorCatalogByCode(Integer code, ColorCatalog colorCatalog) throws NotFoundException, UpdateException {
        ColorCatalog colorCatalogByCode = getColorCatalogByCode(code);
        try {
            colorCatalogByCode.setName(colorCatalog.getName());
            colorCatalogByCode.setColorCode(colorCatalog.getColorCode());
            colorCatalogByCode.setUpdatedBy(colorCatalog.getUpdatedBy());
            colorCatalogByCode.setUpdatedAt(LocalDateTime.now());
            this.colorCatalogRepository.save(colorCatalogByCode);
        } catch (Exception e) {
            log.error("Error to updateColorCatalogByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deleteColorCatalogByCode(Integer code, String userName) throws NotFoundException, UpdateException {
        ColorCatalog colorCatalog = getColorCatalogByCode(code);
        try {
            colorCatalog.setUpdatedBy(userName);
            colorCatalog.setUpdatedAt(LocalDateTime.now());
            colorCatalog.setValidTo(LocalDateTime.now());
            this.colorCatalogRepository.save(colorCatalog);
        } catch (Exception e) {
            log.error("Error to deleteColorCatalogByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
        }
    }

    private ColorCatalog getColorCatalogByCode(Integer code) throws NotFoundException {
        Optional<ColorCatalog> colorCatalog = this.colorCatalogRepository.findByIdAndValidToIsNull(code);
        if(colorCatalog.isPresent()){
            return colorCatalog.get();
        }else {
            log.info("Error to getColorCatalogByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
