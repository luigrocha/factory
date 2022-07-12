package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.vo.req.LocationReq;
import org.crsoft.cartonplast.vo.res.LocationRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ILocationService {

    Collection<LocationRes> findAllLocation();

    LocationRes createLocation(LocationReq location);

    LocationRes findLocationByCode(Integer code);

    LocationRes updateLocationByCode(Integer code, LocationReq location);

    boolean deleteLocationByCode(Integer code);

    Location getLocationByCode(Integer code);
}
