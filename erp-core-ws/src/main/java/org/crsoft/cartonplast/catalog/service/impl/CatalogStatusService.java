package org.crsoft.cartonplast.catalog.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.catalog.repository.CatalogStatusRepository;
import org.crsoft.cartonplast.catalog.service.ICatalogStatusService;
import org.crsoft.cartonplast.catalog.service.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

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
    public Collection<CatalogStatusRes> findAllStatusByType(String type) {
        Collection<CatalogStatus> catalogStatuses = this.catalogStatusRepository.findAllByTypeAndValidToIsNull(type);
        return this.catalogStatusMapper.dieStatusCollectionToDieStatusResCollection(catalogStatuses);
    }

    @Override
    public Optional<CatalogStatus> findByTypeAndIsDefault(String type) {
        return catalogStatusRepository.findByTypeAndIsDefault(type);
    }

    @Override
    public Optional<CatalogStatus> findById(String id) {
        return catalogStatusRepository.findById(id);
    }

    @Override
    public List<CatalogStatus> findByIds(List<String> ids) {
        return catalogStatusRepository.findAllById(ids);
    }
}
