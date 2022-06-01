package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.celler.repository.CellerRepository;
import org.crsoft.cartonplast.celler.service.ICellerService;
import org.crsoft.cartonplast.celler.service.mapper.CellerMapper;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 31/5/2022
 */
@Service
@Slf4j
public class CellerService implements ICellerService {

    private final CellerRepository cellerRepository;
    private final CellerMapper cellerMapper;
    private final MaterialService materialService;

    public CellerService(CellerRepository cellerRepository, CellerMapper cellerMapper, MaterialService materialService) {
        this.cellerRepository = cellerRepository;
        this.cellerMapper = cellerMapper;
        this.materialService = materialService;
    }

    @Override
    public Collection<CellerRes> findAllCeller() throws NotFoundException {
        Collection<Celler> cellers = this.cellerRepository.findAllByValidToIsNullOrderByCreatedAtDesc();
        if (CollectionUtil.isNotEmpty(cellers)) {
            return this.cellerMapper.cellerCollectionToCellerResCollection(cellers);
        } else {
            log.error("Error to findAllCeller");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Celler getCellarByCode(Integer code) throws NotFoundException {
        Optional<Celler> celler = this.cellerRepository.findByIdAndValidToIsNull(code);
        if (celler.isPresent()) {
            return celler.get();
        } else {
            log.error("Error to getCellarByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<CellerRes> findCellerByMaterialCode(Integer id) throws NotFoundException {
        Material material = this.materialService.getMaterialByCode(id);
        Collection<Celler> cellers = this.cellerRepository.findAllByMaterialAndValidToIsNullOrderByCreatedAtDesc(material);
        if (CollectionUtil.isNotEmpty(cellers)) {
            return this.cellerMapper.cellerCollectionToCellerResCollection(cellers);
        } else {
            log.error("Error to findCellerByMaterialCode {}", id);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

}
