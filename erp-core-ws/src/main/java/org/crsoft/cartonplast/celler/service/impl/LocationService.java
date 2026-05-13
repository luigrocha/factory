package org.crsoft.cartonplast.celler.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.celler.repository.LocationRepository;
import org.crsoft.cartonplast.celler.service.ILocationService;
import org.crsoft.cartonplast.celler.service.mapper.LocationMapper;
import org.crsoft.cartonplast.common.exception.*;
import org.crsoft.cartonplast.vo.req.LocationReq;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.*;

/**
 * @author jyepez on 31/5/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class LocationService implements ILocationService {

    private final LocationRepository locationRepository;
    private final LocationMapper locationMapper;

    @Override
    public Collection<LocationRes> findAllLocation() {
        Collection<Location> locations = this.locationRepository.findAllByValidToIsNullOrderByLocationAsc();
        return this.locationMapper.locationCollectionToLocationResCollection(locations);
    }

    @Override
    @Transactional
    public LocationRes createLocation(LocationReq location) {
        return this.locationMapper.locationToLocationRes(
                this.locationRepository.save(this.locationMapper.locationReqToLocation(location))
        );
    }

    @Override
    public LocationRes findLocationByCode(Integer code) {
        return this.locationMapper.locationToLocationRes(getLocationByCode(code));
    }

    @Override
    @Transactional
    public LocationRes updateLocationByCode(Integer code, LocationReq locationReq) {
        Location locationByCode = getLocationByCode(code);
        locationByCode.setLocation(locationReq.getLocation());
        locationByCode.setDescription(locationReq.getDescription());

        return this.locationMapper.locationToLocationRes(
                this.locationRepository.save(locationByCode)
        );
    }

    @Override
    @Transactional
    public boolean deleteLocationByCode(Integer code) {
        return locationRepository.findById(code)
                .map(machine -> {
                    machine.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    @Override
    public Location getLocationByCode(Integer code) {
        Optional<Location> location = this.locationRepository.findByIdAndValidToIsNull(code);
        if (location.isPresent()) {
            return location.get();
        } else {
            log.error("Error to getLocationByCode {}", code);
            throw new BusinessException(BusinessExceptionReason.LOCATION_NOT_FOUND, code);
        }
    }
}
