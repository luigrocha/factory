package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.ColorCatalog;
import org.crsoft.cartonplast.design.repository.ColorCatalogRepository;
import org.crsoft.cartonplast.design.service.IColorCatalogService;
import org.crsoft.cartonplast.design.service.mapper.ColorCatalogMapper;
import org.crsoft.cartonplast.vo.req.ColorCatalogReq;
import org.crsoft.cartonplast.vo.res.ColorCatalogRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 30/5/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ColorCatalogService implements IColorCatalogService {

    private final ColorCatalogRepository colorCatalogRepository;
    private final ColorCatalogMapper colorCatalogMapper;

    @Override
    public Collection<ColorCatalogRes> findAllValidColors() {
        return this.colorCatalogMapper.colorCatalogListToColorCatalogResList(
                this.colorCatalogRepository.findAllByValidToIsNull()
        );
    }

    @Override
    @Transactional
    public ColorCatalogRes createColorCatalog(ColorCatalogReq colorCatalogReq) {
        return this.colorCatalogMapper.colorCatalogToColorCatalogRes(
                this.colorCatalogRepository.save(this.colorCatalogMapper.colorCatalogReqToColorCatalog(colorCatalogReq))
        );
    }

    @Override
    public ColorCatalogRes findColorCatalogByCode(Integer code) {
        return this.colorCatalogMapper.colorCatalogToColorCatalogRes(getColorCatalogByCode(code));
    }

    @Override
    @Transactional
    public ColorCatalogRes updateColorCatalogByCode(
            Integer code, ColorCatalogReq colorCatalogReq) {
        ColorCatalog colorCatalogByCode = getColorCatalogByCode(code);
        colorCatalogByCode.setName(colorCatalogReq.getName());
        colorCatalogByCode.setColorCode(colorCatalogReq.getColorCode());

        return this.colorCatalogMapper.colorCatalogToColorCatalogRes(
                this.colorCatalogRepository.save(colorCatalogByCode)
        );
    }

    @Override
    @Transactional
    public boolean deleteColorCatalogByCode(Integer code) {
        return colorCatalogRepository.findById(code)
                .map(machine -> {
                    machine.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    private ColorCatalog getColorCatalogByCode(Integer code) {
        Optional<ColorCatalog> colorCatalog = this.colorCatalogRepository.findByIdAndValidToIsNull(code);
        if (colorCatalog.isPresent()) {
            return colorCatalog.get();
        } else {
            log.info("Error to getColorCatalogByCode {}", code);
            throw new BusinessException(BusinessExceptionReason.COLOR_CATALOG_NOT_FOUND, code);
        }
    }
}
