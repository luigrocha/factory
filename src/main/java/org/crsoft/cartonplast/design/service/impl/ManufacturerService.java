package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.design.repository.ManufacturerRepository;
import org.crsoft.cartonplast.design.service.IManufacturerService;
import org.crsoft.cartonplast.design.service.mapper.ManufacturerMapper;
import org.crsoft.cartonplast.vo.res.ManufacturerRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 14/06/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerService implements IManufacturerService {

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Override
    public List<ManufacturerRes> findAllValidManufacturers() {
        return this.manufacturerMapper.manufacturersToManufacturersRes(this.manufacturerRepository.findAllValidManufacturers());
    }
}
