package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.celler.repository.LocationRepository;
import org.crsoft.cartonplast.celler.service.ILocationService;
import org.crsoft.cartonplast.celler.service.mapper.LocationMapper;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 31/5/2022
 */
@Service
@Slf4j
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    public LocationService(LocationRepository locationRepository, LocationMapper locationMapper) {
        this.locationRepository = locationRepository;
        this.locationMapper = locationMapper;
    }

    @Override
    public Collection<LocationRes> findAllLocation() throws NotFoundException {
        Collection<Location> locations = this.locationRepository.findAllByValidToIsNullOrderByLocationAsc();
        if (CollectionUtil.isNotEmpty(locations)) {
            return this.locationMapper.locationCollectionToLocationResCollection(locations);
        } else {
            log.error("Error to findAllLocation");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Location getLocationByCode(Integer code) throws NotFoundException {
        Optional<Location> location = this.locationRepository.findByIdAndValidToIsNull(code);
        if (location.isPresent()) {
            return location.get();
        } else {
            log.error("Error to getLocationByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
