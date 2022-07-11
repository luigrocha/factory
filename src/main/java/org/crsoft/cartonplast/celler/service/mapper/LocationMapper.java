package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.vo.req.LocationReq;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationRes locationToLocationRes(Location location);

    @WithoutAuditField
    Location locationReqToLocation(LocationReq locationReq);

    Collection<LocationRes> locationCollectionToLocationResCollection(Collection<Location> location);

}
