package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Manufacturer;
import org.crsoft.cartonplast.design.repository.ManufacturerRepository;
import org.crsoft.cartonplast.design.service.IManufacturerService;
import org.crsoft.cartonplast.design.service.mapper.ManufacturerMapper;
import org.crsoft.cartonplast.vo.req.ManufacturerReq;
import org.crsoft.cartonplast.vo.res.ManufacturerRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.*;

/**
 * @author lpillaga on 14/06/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ManufacturerService implements IManufacturerService {

    private static final String TABLE_NAME = "CATFAB";

    private final ManufacturerRepository manufacturerRepository;
    private final ManufacturerMapper manufacturerMapper;

    @Override
    public Manufacturer getManufacturerByCode(Integer code) throws NotFoundException {
        Optional<Manufacturer> manufacturerOpt = this.manufacturerRepository.findByIdAndValidToIsNullOrValidToGreaterThan(code, LocalDateTime.now());
        if(manufacturerOpt.isPresent()) {
            return manufacturerOpt.get();
        } else {
            log.error("Error to getLocationByCode: {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public List<ManufacturerRes> findAllValidManufacturers() throws NotFoundException {
        List<Manufacturer> manufacturers = this.manufacturerRepository.findAllValidManufacturers();
        if(!manufacturers.isEmpty()) {
            return this.manufacturerMapper.manufacturersToManufacturersRes(manufacturers);
        } else {
            log.error("Error to findAllValidManufacturers");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createManufacturer(ManufacturerReq manufacturerReq) throws InsertException {
        try {
            this.manufacturerRepository.save(this.manufacturerMapper.manufacturerReqToManufacturer(manufacturerReq));
        } catch (Exception e) {
            log.error("Error to createManufacturer: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public ManufacturerRes findManufacturerByCode(Integer code) throws NotFoundException {
        return this.manufacturerMapper.manufacturerToManufacturerRes(getManufacturerByCode(code));
    }

    @Override
    public void updateManufacturerByCode(Integer code, ManufacturerReq manufacturerReq) throws NotFoundException, UpdateException {
        Manufacturer manufacturerBD = getManufacturerByCode(code);
        try {
            manufacturerBD.setName(manufacturerReq.getName());
            this.manufacturerRepository.save(manufacturerBD);
        } catch (Exception e) {
            log.error("Error to updateManufacturer: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deleteManufacturerByCode(Integer code) throws NotFoundException, UpdateException {
        Manufacturer manufacturer = getManufacturerByCode(code);
        try {
            manufacturer.setValidTo(LocalDateTime.now());
            this.manufacturerRepository.save(manufacturer);
        } catch (Exception e) {
            log.error("Error to deleteManufacturerByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
        }
    }
}
