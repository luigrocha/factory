package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.LocationRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ILocationService {

    Collection<LocationRes> findAllLocation() throws NotFoundException;

    Location getLocationByCode(Integer code) throws NotFoundException;

}
