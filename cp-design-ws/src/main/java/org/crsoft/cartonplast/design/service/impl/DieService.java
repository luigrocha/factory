package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.model.Die;
import org.crsoft.cartonplast.design.repository.DieRepository;
import org.crsoft.cartonplast.design.service.IDieService;
import org.crsoft.cartonplast.design.service.mapper.DieMapper;
import org.crsoft.cartonplast.design.vo.res.DieRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class DieService implements IDieService {

    private final DieRepository dieRepository;
    private final DieMapper dieMapper;

    public DieService(
            DieRepository dieRepository,
            DieMapper dieMapper) {
        this.dieRepository = dieRepository;
        this.dieMapper = dieMapper;
    }

    @Override
    public Page<DieRes> findAllValidDies(Pageable pageable, String query) {
        Page<Die> dies = dieRepository.findAllValidDies(pageable, query);
        return dies.map(dieMapper::dieToDieRes);
    }
}
