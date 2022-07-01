package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@Mapper(componentModel = "spring")
public interface LocationMapper {

    LocationRes locationToLocationRes(Location location);

    Collection<LocationRes> locationCollectionToLocationResCollection(Collection<Location> location);

}
