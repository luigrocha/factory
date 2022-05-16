package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.repository.HomoPolymerRepository;
import org.crsoft.cartonplast.design.service.IHomoPolymerService;
import org.crsoft.cartonplast.design.service.mapper.HomoPolymerMapper;
import org.crsoft.cartonplast.design.vo.res.HomoPolymerRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Service
public class HomoPolymerService implements IHomoPolymerService {

    private final HomoPolymerRepository homoPolymerRepository;
    private final HomoPolymerMapper homoPolymerMapper;

    public HomoPolymerService(
            HomoPolymerRepository homoPolymerRepository,
            HomoPolymerMapper homoPolymerMapper) {
        this.homoPolymerRepository = homoPolymerRepository;
        this.homoPolymerMapper = homoPolymerMapper;
    }

    @Override
    public List<HomoPolymerRes> findAllValidHomopolymers() {
        return this.homoPolymerMapper.homoPolymersToHomoPolymersRes(
                homoPolymerRepository.findAllValidHomoPolymers()
        );
    }
}
