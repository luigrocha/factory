package org.crsoft.cartonplast.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.catalog.repository.CatalogPriorityRepository;
import org.crsoft.cartonplast.catalog.service.ICatalogPriorityService;
import org.crsoft.cartonplast.catalog.service.mapper.CatalogPriorityMapper;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jyepez on 25/5/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class CatalogPriorityService implements ICatalogPriorityService {

    private final CatalogPriorityRepository catalogPriorityRepository;
    private final CatalogPriorityMapper catalogPriorityMapper;

    @Override
    public List<CatalogPriorityRes> findAllPriorityByType(String type) {
        List<CatalogPriority> catalogPriority = this.catalogPriorityRepository.findAllByTypeOrderByIndex(type);
        return this.catalogPriorityMapper.catalogPriorityCollectionToCatalogResCollection(catalogPriority);
    }

    @Override
    public CatalogPriority findPriorityById(String id) {
        return this.catalogPriorityRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("Priority not found with id: {}", id);
                    return new BusinessException(BusinessExceptionReason.CATALOG_PRIORITY_NOT_FOUND, id);
                });
    }
}
