package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.model.Cyrel;
import org.crsoft.cartonplast.design.repository.CyrelRepository;
import org.crsoft.cartonplast.design.service.ICyrelService;
import org.crsoft.cartonplast.design.service.mapper.CyrelMapper;
import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;


/**
 * @author lpillaga on 12/05/2022
 */
@Service
public class CyrelService implements ICyrelService {

    private final CyrelRepository cyrelRepository;
    private final CyrelMapper cyrelMapper;

    public CyrelService(
            CyrelRepository cyrelRepository,
            CyrelMapper cyrelMapper) {
        this.cyrelRepository = cyrelRepository;
        this.cyrelMapper = cyrelMapper;
    }

    @Override
    public Page<CyrelRes> findAllValidCyrels(Pageable pageable, String query) {
        Page<Cyrel> cyrels = cyrelRepository.findAllValidCyrels(pageable, query);
        return cyrels.map(cyrelMapper::cyrelToCyrelRes);
    }
}
