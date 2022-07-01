package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.res.ManufacturerRes;

import java.util.List;

/**
 * @author lpillaga on 14/06/2022
 */
public interface IManufacturerService {

    List<ManufacturerRes> findAllValidManufacturers();
}
