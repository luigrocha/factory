package org.crsoft.cartonplast.common.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.model.TypeCellar;
import org.crsoft.cartonplast.common.repository.TypeCellarRepository;
import org.crsoft.cartonplast.common.service.ITypeCellarService;
import org.crsoft.cartonplast.common.service.mapper.TypeCellarMapper;
import org.crsoft.cartonplast.vo.res.TypeCellarRes;
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
public class TypeCellarService implements ITypeCellarService {

    private final TypeCellarRepository typeCellarRepository;
    private final TypeCellarMapper typeCellarMapper;

    public TypeCellarService(TypeCellarRepository typeCellarRepository, TypeCellarMapper typeCellarMapper) {
        this.typeCellarRepository = typeCellarRepository;
        this.typeCellarMapper = typeCellarMapper;
    }

    @Override
    public Collection<TypeCellarRes> findAllTypeCellar() throws NotFoundException {
        Collection<TypeCellar> typeCellars = this.typeCellarRepository.findAllByValidToIsNullOrderByIdAsc();
        if (CollectionUtil.isNotEmpty(typeCellars)) {
            return this.typeCellarMapper.typeCellarCollectionToTypeCellarResCollection(typeCellars);
        } else {
            log.error("Error to findAllTypeCellar");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public TypeCellar findCellarById(Integer id) throws NotFoundException {
        Optional<TypeCellar> typeCellar = this.typeCellarRepository.findByIdAndValidToIsNull(id);
        if (typeCellar.isPresent()) {
            return typeCellar.get();
        } else {
            log.error("Error to findCellarById {}", id);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
