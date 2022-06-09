package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.celler.repository.LocationRepository;
import org.crsoft.cartonplast.celler.service.ILocationService;
import org.crsoft.cartonplast.celler.service.mapper.LocationMapper;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.*;

/**
 * @author jyepez on 31/5/2022
 */
@Service
@Slf4j
public class LocationService implements ILocationService {

    private static final String TABLE_NAME = "CDTLOC";
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
    public void createLocation(Location location) throws InsertException {
        try {
            this.locationRepository.save(location);
        } catch (Exception e) {
            log.error("Error to createLocation: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public LocationRes findLocationByCode(Integer code) throws NotFoundException {
        return this.locationMapper.locationToLocationRes(getLocationByCode(code));
    }

    @Override
    public void updateLocationByCode(Integer code, Location location) throws NotFoundException, UpdateException {
        Location locationByCode = getLocationByCode(code);
        try {
            locationByCode.setLocation(location.getLocation());
            locationByCode.setDescription(location.getDescription());
            locationByCode.setUpdatedBy(location.getUpdatedBy());
            locationByCode.setUpdatedAt(LocalDateTime.now());
            this.locationRepository.save(locationByCode);
        } catch (Exception e) {
            log.error("Error to updateLocationByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deleteLocationByCode(Integer code, String userName) throws NotFoundException, UpdateException {
        Location location = getLocationByCode(code);
        try {
            location.setUpdatedBy(userName);
            location.setUpdatedAt(LocalDateTime.now());
            location.setValidTo(LocalDateTime.now());
            this.locationRepository.save(location);
        }catch (Exception e) {
            log.error("Error to deleteLocationByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
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
