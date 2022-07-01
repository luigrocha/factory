package org.crsoft.cartonplast.catalog.service.impl;

import org.crsoft.cartonplast.catalog.service.ICatalogPriorityService;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.catalog.repository.CatalogPriorityRepository;
import org.crsoft.cartonplast.catalog.service.mapper.CatalogPriorityMapper;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 25/5/2022
 */
@Service
public class CatalogPriorityService implements ICatalogPriorityService {

    private final CatalogPriorityRepository catalogPriorityRepository;
    private final CatalogPriorityMapper catalogPriorityMapper;

    public CatalogPriorityService(CatalogPriorityRepository catalogPriorityRepository, CatalogPriorityMapper catalogPriorityMapper) {
        this.catalogPriorityRepository = catalogPriorityRepository;
        this.catalogPriorityMapper = catalogPriorityMapper;
    }

    @Override
    public Collection<CatalogPriorityRes> findAllPriorityByType(String type) throws NotFoundException {
        Collection<CatalogPriority> catalogPriority = this.catalogPriorityRepository.findAllByTypeAndValidToIsNull(type);
        if (CollectionUtil.isNotEmpty(catalogPriority)) {
            return this.catalogPriorityMapper.catalogPriorityCollectionToCatalogResCollection(catalogPriority);
        } else {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
