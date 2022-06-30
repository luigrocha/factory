package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.crsoft.cartonplast.celler.repository.TypeMaterialRepository;
import org.crsoft.cartonplast.celler.service.ITypeMaterialService;
import org.crsoft.cartonplast.celler.service.mapper.TypeMaterialMapper;
import org.crsoft.cartonplast.common.constant.MessagesConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.TypeMaterialRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;


/**
 * @author jyepez on 27/5/2022
 */
@Service
@Slf4j
public class TypeMaterialService implements ITypeMaterialService {

    private final TypeMaterialRepository typeMaterialRepository;
    private final TypeMaterialMapper typeMaterialMapper;

    public TypeMaterialService(TypeMaterialRepository typeMaterialRepository, TypeMaterialMapper typeMaterialMapper) {
        this.typeMaterialRepository = typeMaterialRepository;
        this.typeMaterialMapper = typeMaterialMapper;
    }

    @Override
    public Collection<TypeMaterialRes> findAllTypeCellar() throws NotFoundException {
        Collection<TypeMaterial> typeMaterials = this.typeMaterialRepository.findAllByValidToIsNullOrderByIdAsc();
        if (CollectionUtil.isNotEmpty(typeMaterials)) {
            return this.typeMaterialMapper.typeMaterialCollectionToTypeMaterialResCollection(typeMaterials);
        } else {
            log.error("Error to findAllTypeCellar");
            throw new NotFoundException(MessagesConstant.MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public TypeMaterial findCellarById(Integer id) throws NotFoundException {
        Optional<TypeMaterial> typeCellar = this.typeMaterialRepository.findByIdAndValidToIsNull(id);
        if (typeCellar.isPresent()) {
            return typeCellar.get();
        } else {
            log.error("Error to findCellarById {}", id);
            throw new NotFoundException(MessagesConstant.MESSAGE_NOT_FOUND);
        }
    }
}
