package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.vo.res.LocationRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ILocationService {

    Collection<LocationRes> findAllLocation() throws NotFoundException;

    void createLocation(Location location) throws InsertException;

    LocationRes findLocationByCode(Integer code) throws NotFoundException;

    void updateLocationByCode(Integer code, Location location) throws NotFoundException, UpdateException;

    void deleteLocationByCode(Integer code, String userName) throws NotFoundException, UpdateException;

    Location getLocationByCode(Integer code) throws NotFoundException;
}
