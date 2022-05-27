package org.crsoft.cartonplast.common.service.impl;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.model.CatalogPriority;
import org.crsoft.cartonplast.common.repository.CatalogPriorityRepository;
import org.crsoft.cartonplast.common.service.ICatalogPriorityService;
import org.crsoft.cartonplast.common.service.mapper.CatalogPriorityMapper;
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
