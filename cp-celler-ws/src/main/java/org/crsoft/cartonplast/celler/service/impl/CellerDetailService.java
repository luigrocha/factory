package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.*;
import org.crsoft.cartonplast.celler.repository.CellerDetailRepository;
import org.crsoft.cartonplast.celler.service.ICellerDetailService;
import org.crsoft.cartonplast.celler.service.mapper.CellerDetailMapper;
import org.crsoft.cartonplast.celler.util.DocumentEnum;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.CellerDetailReq;
import org.crsoft.cartonplast.vo.res.CellerDetailRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_INSERT;
import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 31/5/2022
 */
@Service
@Slf4j
public class CellerDetailService implements ICellerDetailService {

    private static final String TABLE_NAME = "CATCELL_DET";
    private final CellerDetailRepository cellerDetailRepository;
    private final CellerDetailMapper cellerDetailMapper;
    private final MaterialService materialService;
    private final DocumentService documentService;
    private final LocationService locationService;
    private final CellerService cellerService;

    public CellerDetailService(CellerDetailRepository cellerDetailRepository, CellerDetailMapper cellerDetailMapper, MaterialService materialService, DocumentService documentService, LocationService locationService, CellerService cellerService) {
        this.cellerDetailRepository = cellerDetailRepository;
        this.cellerDetailMapper = cellerDetailMapper;
        this.materialService = materialService;
        this.documentService = documentService;
        this.locationService = locationService;
        this.cellerService = cellerService;
    }

    @Override
    public CellerDetail getCellarDetailByCode(Integer code) throws NotFoundException {
        Optional<CellerDetail> celler = this.cellerDetailRepository.findByIdAndValidToIsNull(code);
        if (celler.isPresent()) {
            return celler.get();
        } else {
            log.error("Error to getCellarDetailByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<CellerDetailRes> findByLocationCode(Integer codeLocation, Integer codeMaterial) throws NotFoundException {
        Location location = this.locationService.getLocationByCode(codeLocation);
        Material material = this.materialService.getMaterialByCode(codeMaterial);
        Collection<CellerDetail> cellerDetails = this.cellerDetailRepository.findAllByLocationAndMaterialAndValidToIsNull(location, material);
        if (CollectionUtil.isNotEmpty(cellerDetails)) {
            return this.cellerDetailMapper.cellerDetailCollectionToCellerDetailResCollection(cellerDetails);
        } else {
            log.error("Error to findByLocationCode {} - {}", codeLocation, codeMaterial);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<CellerDetailRes> findCellerDetailByMaterialCode(Integer id) throws NotFoundException {
        Material material = this.materialService.getMaterialByCode(id);
        Collection<CellerDetail> cellers = this.cellerDetailRepository.findAllByMaterialAndValidToIsNullOrderByCreatedAtDesc(material);
        if (CollectionUtil.isNotEmpty(cellers)) {
            return this.cellerDetailMapper.cellerDetailCollectionToCellerDetailResCollection(cellers);
        } else {
            log.error("Error to findCellerDetailByMaterialCode {}", id);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<CellerDetailRes> findCellerDetailByCellerCode(Integer code) throws NotFoundException {
        Celler celler = this.cellerService.getCellarByCode(code);
        Collection<CellerDetail> cellerDetailRes = this.cellerDetailRepository.findAllByCellerAndValidToIsNull(celler);
        if (CollectionUtil.isNotEmpty(cellerDetailRes)) {
            return this.cellerDetailMapper.cellerDetailCollectionToCellerDetailResCollection(cellerDetailRes);
        } else {
            log.error("Error to findCellerDetailByCellerCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createCellerDetail(Collection<CellerDetailReq> cellers,Celler codeCeller,String userName) throws InsertException, NotFoundException {
        Collection<CellerDetail> cellersSave = new ArrayList<>(0);
        for (CellerDetailReq cellerDetail : cellers) {
            cellersSave.add(buildCellerDetailToSave(cellerDetail,codeCeller,userName));
        }
        try {
            this.cellerDetailRepository.saveAll(cellersSave);
        } catch (Exception e) {
            log.error("Error to createCellerDetail: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public Collection<CellerDetailRes> findIfExistStockByMaterialCode(Integer id) throws NotFoundException {
        this.materialService.getMaterialByCode(id);
        return null;
    }

    @Override
    public Collection<CellerDetailRes> findIfExistStockByMaterialAndLote(Integer material, String lote) {
        return null;
    }

    private CellerDetail buildCellerDetailToSave(CellerDetailReq cellerDetailReq,Celler codeCeller,String userName) throws NotFoundException {
        Material material = this.materialService.getMaterialByCode(cellerDetailReq.getMaterial());
        Document document = this.documentService.getDocumentById(cellerDetailReq.getDocument());
        Location location = this.locationService.getLocationByCode(cellerDetailReq.getLocation());
        String loteString = String.valueOf(cellerDetailReq.getLote());
        if(document.getName().equals(DocumentEnum.CEB.getName()) ||
                document.getName().equals(DocumentEnum.TM5.getName()) ||
                document.getName().equals(DocumentEnum.MOV.getName())){
            loteString = getCellarDetailByCode((Integer) cellerDetailReq.getLote()).getLote();
        }
        CellerDetail cellerDetail = new CellerDetail();
        cellerDetail.setLote(loteString);
        cellerDetail.setAmount(cellerDetailReq.getAmount());
        cellerDetail.setBalance(cellerDetailReq.getBalance());
        cellerDetail.setCoat(cellerDetailReq.getCoat());
        cellerDetail.setPallets(cellerDetailReq.getPallets());
        cellerDetail.setWeight(cellerDetailReq.getWeight());
        cellerDetail.setCeller(codeCeller);
        cellerDetail.setMaterial(material);
        cellerDetail.setLocation(location);
        cellerDetail.setDocument(document);
        cellerDetail.setCreatedBy(userName);
        return cellerDetail;
    }
}
