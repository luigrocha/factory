package org.crsoft.cartonplast.catalog.service.impl;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.catalog.repository.CatalogPriorityRepository;
import org.crsoft.cartonplast.catalog.service.ICatalogPriorityService;
import org.crsoft.cartonplast.catalog.service.mapper.CatalogPriorityMapper;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author jyepez on 25/5/2022
 */
@Service
@RequiredArgsConstructor
public class CatalogPriorityService implements ICatalogPriorityService {

    private final CatalogPriorityRepository catalogPriorityRepository;
    private final CatalogPriorityMapper catalogPriorityMapper;

    @Override
    public List<CatalogPriorityRes> findAllPriorityByType(String type) {
        List<CatalogPriority> catalogPriority = this.catalogPriorityRepository.findAllByTypeOrderByIndex(type);
        return this.catalogPriorityMapper.catalogPriorityCollectionToCatalogResCollection(catalogPriority);
    }
}
