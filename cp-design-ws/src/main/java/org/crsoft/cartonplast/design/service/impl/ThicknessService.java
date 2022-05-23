package org.crsoft.cartonplast.design.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Thickness;
import org.crsoft.cartonplast.design.repository.ThicknessRepository;
import org.crsoft.cartonplast.design.service.IThicknessService;
import org.crsoft.cartonplast.design.service.mapper.ThicknessMapper;
import org.crsoft.cartonplast.design.vo.res.ThicknessRes;
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
public class ThicknessService implements IThicknessService {

    private static final String TABLE_NAME = "CATTHI";
    private final ThicknessRepository thicknessRepository;
    private final ThicknessMapper thicknessMapper;

    public ThicknessService(
            ThicknessRepository thicknessRepository,
            ThicknessMapper thicknessMapper) {
        this.thicknessRepository = thicknessRepository;
        this.thicknessMapper = thicknessMapper;
    }

    @Override
    public Collection<ThicknessRes> findAllValidThickness() throws NotFoundException {
        try {
            return this.thicknessMapper.thicknessesToThicknessesRes(
                    this.thicknessRepository.findAllByValidToIsNullOrderByWeightAsc()
            );
        } catch (Exception e) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createThickness(Thickness thickness) throws InsertException {
        try {
            this.thicknessRepository.save(thickness);
        } catch (Exception e) {
            log.error("Error to createThickness: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public ThicknessRes findThicknessByCode(Integer code) throws NotFoundException {
        return this.thicknessMapper.thicknessToThicknessRes(getThicknessByCode(code));
    }

    @Override
    public void updateThicknessByCode(Integer code, Thickness thickness) throws NotFoundException, UpdateException {
        Thickness thicknessByCode = getThicknessByCode(code);
        try {
            thicknessByCode.setWeight(thickness.getWeight());
            thicknessByCode.setThick(thickness.getThick());
            thicknessByCode.setUpdatedAt(LocalDateTime.now());
            this.thicknessRepository.save(thicknessByCode);
        } catch (Exception e) {
            log.error("Error to updateThicknessByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deleteThicknessByCode(Integer code, String userName) throws NotFoundException, UpdateException {
        Thickness thickness = getThicknessByCode(code);
        try {
            thickness.setUpdatedBy(userName);
            thickness.setUpdatedAt(LocalDateTime.now());
            thickness.setValidTo(LocalDateTime.now());
            this.thicknessRepository.save(thickness);
        } catch (Exception e) {
            log.error("Error to deleteHomoPolymerByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
        }
    }

    private Thickness getThicknessByCode(Integer code) throws NotFoundException {
        Optional<Thickness> thickness = this.thicknessRepository.findByIdAndValidToIsNull(code);
        if (thickness.isPresent()) {
            return thickness.get();
        } else {
            log.info("Error to getThicknessByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

}
