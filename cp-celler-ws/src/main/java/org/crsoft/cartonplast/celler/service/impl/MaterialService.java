package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.crsoft.cartonplast.celler.repository.MaterialRepository;
import org.crsoft.cartonplast.celler.service.IMaterialService;
import org.crsoft.cartonplast.celler.service.mapper.MaterialMapper;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.MaterialRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;


/**
 * @author jyepez on 27/5/2022
 */
@Service
@Slf4j
public class MaterialService implements IMaterialService {

    private final MaterialRepository materialRepository;
    private final TypeMaterialService typeCellarService;
    private final MaterialMapper materialMapper;

    public MaterialService(MaterialRepository materialRepository,
                           TypeMaterialService typeCellarService,
                           MaterialMapper materialMapper) {
        this.materialRepository = materialRepository;
        this.typeCellarService = typeCellarService;
        this.materialMapper = materialMapper;
    }

    @Override
    public Collection<MaterialRes> findAllCatalogCellarByType(Integer id) throws NotFoundException {
        TypeMaterial typeMaterial = this.typeCellarService.findCellarById(id);
        Collection<Material> materials = this.materialRepository.findAllByTypeMaterialAndValidToIsNullOrderByNameAsc(typeMaterial);
        if (CollectionUtil.isNotEmpty(materials)) {
            return this.materialMapper.materialCollectionToMaterialResCollection(materials);
        } else {
            log.error("Error to findAllCatalogCellarByType");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Material getMaterialByCode(Integer code) throws NotFoundException {
        Optional<Material> material = this.materialRepository.findByIdAndValidToIsNull(code);
        if (material.isPresent()) {
            return material.get();
        } else {
            log.error("Error to getMaterialByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<MaterialRes> findIfExistStock(Integer id) throws NotFoundException {
        TypeMaterial typeMaterial = this.typeCellarService.findCellarById(id);
        return null;
    }
}
