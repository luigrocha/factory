package org.crsoft.cartonplast.catalog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.catalog.service.ICatalogStatusService;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.catalog.repository.CatalogStatusRepository;
import org.crsoft.cartonplast.catalog.service.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 26/5/2022
 */
@Service
@Slf4j
public class CatalogStatusService implements ICatalogStatusService {

    private final CatalogStatusRepository catalogStatusRepository;
    private final CatalogStatusMapper catalogStatusMapper;

    public CatalogStatusService(CatalogStatusRepository catalogStatusRepository, CatalogStatusMapper catalogStatusMapper) {
        this.catalogStatusRepository = catalogStatusRepository;
        this.catalogStatusMapper = catalogStatusMapper;
    }

    @Override
    public Collection<CatalogStatusRes> findAllStatusByType(String type) throws NotFoundException {
        Collection<CatalogStatus> catalogStatuses = this.catalogStatusRepository.findAllByTypeAndValidToIsNull(type);
        if (CollectionUtil.isNotEmpty(catalogStatuses)) {
            return this.catalogStatusMapper.dieStatusCollectionToDieStatusResCollection(catalogStatuses);
        } else {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Optional<CatalogStatus> findByTypeAndIsDefault(String type) {
        return catalogStatusRepository.findByTypeAndIsDefault(type);
    }

    @Override
    public Optional<CatalogStatus> findById(String id) {
        return catalogStatusRepository.findById(id);
    }
}
