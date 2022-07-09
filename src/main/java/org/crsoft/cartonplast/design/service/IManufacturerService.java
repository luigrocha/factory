package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Manufacturer;
import org.crsoft.cartonplast.vo.req.ManufacturerReq;
import org.crsoft.cartonplast.vo.res.ManufacturerRes;

import java.util.List;

/**
 * @author lpillaga on 14/06/2022
 */
public interface IManufacturerService {

    List<ManufacturerRes> findAllValidManufacturers() throws NotFoundException;

    void createManufacturer(ManufacturerReq manufacturer) throws InsertException;

    ManufacturerRes findManufacturerByCode(Integer code) throws NotFoundException;

    void updateManufacturerByCode(Integer code, ManufacturerReq manufacturer) throws NotFoundException, UpdateException;

    void deleteManufacturerByCode(Integer code) throws NotFoundException, UpdateException;

    Manufacturer getManufacturerByCode(Integer code) throws NotFoundException;
}
