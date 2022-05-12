package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.repository.ThicknessRepository;
import org.crsoft.cartonplast.design.service.IThicknessService;
import org.crsoft.cartonplast.design.service.mapper.ThicknessMapper;
import org.crsoft.cartonplast.design.vo.res.ThicknessRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Service
public class ThicknessService implements IThicknessService {

    private final ThicknessRepository thicknessRepository;
    private final ThicknessMapper thicknessMapper;

    public ThicknessService(
            ThicknessRepository thicknessRepository,
            ThicknessMapper thicknessMapper) {
        this.thicknessRepository = thicknessRepository;
        this.thicknessMapper = thicknessMapper;
    }

    @Override
    public List<ThicknessRes> findAllValidThickness() {
        return this.thicknessMapper.thicknessesToThicknessesRes(
                this.thicknessRepository.findAllValidThickness()
        );
    }
}
