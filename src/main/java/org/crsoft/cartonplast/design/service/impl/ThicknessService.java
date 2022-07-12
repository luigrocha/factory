package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.Thickness;
import org.crsoft.cartonplast.design.repository.ThicknessRepository;
import org.crsoft.cartonplast.design.service.IThicknessService;
import org.crsoft.cartonplast.design.service.mapper.ThicknessMapper;
import org.crsoft.cartonplast.vo.req.ThicknessReq;
import org.crsoft.cartonplast.vo.res.ThicknessRes;
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
public class ThicknessService implements IThicknessService {

    private final ThicknessRepository thicknessRepository;
    private final ThicknessMapper thicknessMapper;

    @Override
    public Collection<ThicknessRes> findAllValidThickness() {
        return thicknessMapper.thicknessesToThicknessesRes(thicknessRepository.findAllValidThickness());
    }

    @Override
    public ThicknessRes createThickness(ThicknessReq thicknessReq) {
        Thickness thickness = thicknessMapper.thicknessReqToThickness(thicknessReq);
        return this.thicknessMapper.thicknessToThicknessRes(this.thicknessRepository.save(thickness));
    }

    @Override
    public ThicknessRes findThicknessByCode(Integer code) {
        return this.thicknessMapper.thicknessToThicknessRes(getThicknessByCode(code));
    }

    @Override
    @Transactional
    public ThicknessRes updateThicknessByCode(Integer code, ThicknessReq thicknessReq) {
        Thickness thicknessByCode = getThicknessByCode(code);
        thicknessByCode.setThick(thicknessReq.getThick());
        thicknessByCode.setWeight(thicknessReq.getWeight());

        return this.thicknessMapper.thicknessToThicknessRes(thicknessByCode);
    }

    @Override
    @Transactional
    public boolean deleteThicknessByCode(Integer code) {
        Thickness thickness = getThicknessByCode(code);
        thickness.setValidTo(LocalDateTime.now());

        return true;
    }

    private Thickness getThicknessByCode(Integer code) {
        Optional<Thickness> thickness = this.thicknessRepository.findByIdAndValidToIsNull(code);
        if (thickness.isPresent()) {
            return thickness.get();
        } else {
            log.info("Error to getThicknessByCode {}", code);
            throw new BusinessException(BusinessExceptionReason.THICKNESS_NOT_FOUND, code);
        }
    }

}
