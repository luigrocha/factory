package org.crsoft.cartonplast.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.model.CatalogCellar;
import org.crsoft.cartonplast.common.model.TypeCellar;
import org.crsoft.cartonplast.common.repository.CatalogCellarRepository;
import org.crsoft.cartonplast.common.service.ICatalogCellarService;
import org.crsoft.cartonplast.common.service.mapper.CatalogCellarMapper;
import org.crsoft.cartonplast.vo.res.CatalogCellarRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 27/5/2022
 */
@Service
@Slf4j
public class CatalogCellarService implements ICatalogCellarService {

    private final CatalogCellarRepository catalogCellarRepository;
    private final TypeCellarService typeCellarService;
    private final CatalogCellarMapper catalogCellarMapper;

    public CatalogCellarService(CatalogCellarRepository catalogCellarRepository,
                                TypeCellarService typeCellarService,
                                CatalogCellarMapper catalogCellarMapper) {
        this.catalogCellarRepository = catalogCellarRepository;
        this.typeCellarService = typeCellarService;
        this.catalogCellarMapper = catalogCellarMapper;
    }

    @Override
    public Collection<CatalogCellarRes> findAllCatalogCellarByType(Integer id) throws NotFoundException {
        TypeCellar typeCellar = this.typeCellarService.findCellarById(id);
        Collection<CatalogCellar> catalogCellars = this.catalogCellarRepository.findAllByTypeCellarAndValidToIsNullOrderByNameAsc(typeCellar);
        if (CollectionUtil.isNotEmpty(catalogCellars)) {
            return this.catalogCellarMapper.catalogCellarCollectionToCatalogCellarResCollection(catalogCellars);
        } else {
            log.error("Error to findAllCatalogCellarByType");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
