package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.*;
import org.crsoft.cartonplast.celler.repository.CellerDetailRepository;
import org.crsoft.cartonplast.celler.service.ICellerDetailService;
import org.crsoft.cartonplast.celler.service.mapper.CellerDetailMapper;
import org.crsoft.cartonplast.celler.service.mapper.LocationMapper;
import org.crsoft.cartonplast.celler.service.mapper.MaterialMapper;
import org.crsoft.cartonplast.celler.util.DocumentEnum;
import org.crsoft.cartonplast.celler.vo.AllStockVo;
import org.crsoft.cartonplast.celler.vo.LoteStockVo;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.CellerDetailReq;
import org.crsoft.cartonplast.vo.res.*;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Objects;
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
    private final LocationMapper locationMapper;
    private final MaterialMapper materialMapper;

    public CellerDetailService(CellerDetailRepository cellerDetailRepository, CellerDetailMapper cellerDetailMapper,
                               MaterialService materialService, DocumentService documentService,
                               LocationService locationService, CellerService cellerService,
                               LocationMapper locationMapper, MaterialMapper materialMapper) {
        this.cellerDetailRepository = cellerDetailRepository;
        this.cellerDetailMapper = cellerDetailMapper;
        this.materialService = materialService;
        this.documentService = documentService;
        this.locationService = locationService;
        this.cellerService = cellerService;
        this.locationMapper = locationMapper;
        this.materialMapper = materialMapper;
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
    public Collection<CellerDetailRes> findByLocationCode(String lote, Integer codeMaterial) throws NotFoundException {
        Collection<CellerDetail> cellerDetails = this.cellerDetailRepository.findByMaterialAndLote(codeMaterial, lote);
        if (CollectionUtil.isNotEmpty(cellerDetails)) {
            return this.cellerDetailMapper.cellerDetailCollectionToCellerDetailResCollection(cellerDetails);
        } else {
            log.error("Error to findByLocationCode {} - {}", lote, codeMaterial);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<CellerDetailRes> findCellerDetailByMaterialCode(Integer id) throws NotFoundException {
        Collection<LoteStockVo> cellers = this.cellerDetailRepository.findAllLoteStockByMaterial(id);
        if (CollectionUtil.isNotEmpty(cellers)) {
            Collection<CellerDetail> cellerLotes = new ArrayList<>(0);
            for(LoteStockVo loteStockVo:cellers){
                double weight = Objects.isNull(loteStockVo.getWeight()) ? 0L : loteStockVo.getWeight();
                if(weight > 0){
                    CellerDetail cellerDetail = getCellarDetailByCode(loteStockVo.getId());
                    cellerLotes.add(cellerDetail);
                }
            }
            return this.cellerDetailMapper.cellerDetailCollectionToCellerDetailResCollection(cellerLotes);
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
    public void createCellerDetail(Collection<CellerDetailReq> cellers, Celler codeCeller, String userName)
            throws InsertException, NotFoundException {
        Collection<CellerDetail> cellersSave = new ArrayList<>(0);
        for (CellerDetailReq cellerDetail : cellers) {
            cellersSave.add(buildCellerDetailToSave(cellerDetail, codeCeller, userName));
        }
        try {
            this.cellerDetailRepository.saveAll(cellersSave);
        } catch (Exception e) {
            log.error("Error to createCellerDetail: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public CellerStockRes findCellerDetailStock(Integer materialCode, String lote) {
        Collection<LocationStockRes> locationStockRes = new ArrayList<>(0);
        Collection<CellerDetail> locationStock = this.cellerDetailRepository.findLocationStock(materialCode, lote);
        Collection<CellerDetail> detailStock = this.cellerDetailRepository.findDetailStock(materialCode, lote);

        MaterialRes materialRes = this.materialMapper.materialToMaterialRes(
                locationStock.stream().findFirst().get().getMaterial());

        double sumStock = 0L;

        for (CellerDetail detail : detailStock) {
            sumStock += detail.getWeight();
        }

        for (CellerDetail location : locationStock) {
            double sumStockLocation = 0L;
            for (CellerDetail detail : detailStock) {
                if (detail.getLocation().getId().equals(location.getLocation().getId())) {
                    sumStockLocation += detail.getWeight();
                }
            }
            locationStockRes.add(LocationStockRes.builder()
                    .location(this.locationMapper.locationToLocationRes(location.getLocation()))
                    .stock(sumStockLocation)
                    .build());
        }

        return CellerStockRes.builder()
                .material(materialRes)
                .locationStock(locationStockRes)
                .stock(sumStock)
                .build();
    }



    @Override
    public Collection<AllStockVo> findByTypeMaterialStock(Integer typeCode) throws NotFoundException {
        Collection<AllStockVo> typeMaterialStock = this.cellerDetailRepository.findByTypeMaterialStock(typeCode);
        if(CollectionUtil.isNotEmpty(typeMaterialStock)){
            return  typeMaterialStock;
        } else {
            log.error("Error to findByTypeMaterialStock {}", typeCode);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<AllStockVo> findAllStock() throws NotFoundException {
        Collection<AllStockVo> allStock = this.cellerDetailRepository.findAllStock();
        if(CollectionUtil.isNotEmpty(allStock)){
            return  allStock;
        } else {
            log.error("Error to findAllStock ");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<AllStockVo> findMaterialStock(Integer code) throws NotFoundException {
        Collection<AllStockVo> typeMaterialStock = this.cellerDetailRepository.findMaterialStock(code);
        if(CollectionUtil.isNotEmpty(typeMaterialStock)){
            return  typeMaterialStock;
        } else {
            log.error("Error to findMaterialStock {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<AllStockVo> findMaterialLoteStock(Integer code, String lote) throws NotFoundException {
        Collection<AllStockVo> typeMaterialStock = this.cellerDetailRepository.findMaterialLoteStock(code,lote);
        if(CollectionUtil.isNotEmpty(typeMaterialStock)){
            return  typeMaterialStock;
        } else {
            log.error("Error to findMaterialLoteStock {} - {}", code, lote);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<CellerLoteRes> findLoteByMaterialCode(Integer code) throws NotFoundException {
        Collection<CellerDetail> cellers = this.cellerDetailRepository.findAllLoteStock(code);
        if (CollectionUtil.isNotEmpty(cellers)) {
            return this.cellerDetailMapper.cellerDetailCollectionToCellerLoteResCollection(cellers);
        } else {
            log.error("Error to findLoteByMaterialCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<LoteStockVo> findByMaterialStock(Integer code) throws NotFoundException {
        Collection<LoteStockVo> loteStockVos = this.cellerDetailRepository.findAllLoteStockByMaterial(code);
        if(CollectionUtil.isNotEmpty(loteStockVos)){
            return loteStockVos;
        }else {
            log.error("Error to findByMaterialStock {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public Collection<AllStockVo> findTotalStockByMaterial(Integer code) throws NotFoundException {
        Collection<AllStockVo> totalStock = this.cellerDetailRepository.findTotalStockByMaterial(code);
        if(CollectionUtil.isNotEmpty(totalStock)){
            return totalStock;
        }else {
            log.error("Error to findTotalStockByMaterial {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    private CellerDetail buildCellerDetailToSave(CellerDetailReq cellerDetailReq, Celler codeCeller,
                                                 String userName) throws NotFoundException {
        Material material = this.materialService.getMaterialByCode(cellerDetailReq.getMaterial());
        Document document = this.documentService.getDocumentById(cellerDetailReq.getDocument());
        Location location = this.locationService.getLocationByCode(cellerDetailReq.getLocation());
        String loteString = String.valueOf(cellerDetailReq.getLote());
        if (document.getName().equals(DocumentEnum.CEB.getName()) ||
                document.getName().equals(DocumentEnum.TM5.getName()) ||
                document.getName().equals(DocumentEnum.MOV.getName())) {
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
