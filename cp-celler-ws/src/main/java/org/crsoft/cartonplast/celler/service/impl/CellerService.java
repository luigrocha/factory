package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.*;
import org.crsoft.cartonplast.celler.repository.CellerRepository;
import org.crsoft.cartonplast.celler.service.ICellerService;
import org.crsoft.cartonplast.celler.service.mapper.CellerMapper;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptItemReq;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.CellerDetailReq;
import org.crsoft.cartonplast.vo.req.CellerReq;
import org.crsoft.cartonplast.vo.res.CellerDetailRes;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.crsoft.cartonplast.vo.res.CodeDocumentRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_INSERT;
import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 31/5/2022
 */
@Service
@Slf4j
public class CellerService implements ICellerService {

    private static final String TABLE_NAME = "CATCELL";
    private final CellerRepository cellerRepository;
    private final CellerMapper cellerMapper;
    private final DocumentService documentService;
    private final CellerDetailService cellerDetailService;
    private final MaterialService materialService;
    private final LocationService locationService;
    private final OptionDocumentService optionDocumentService;

    public CellerService(CellerRepository cellerRepository, CellerMapper cellerMapper,
                         DocumentService documentService, @Lazy CellerDetailService cellerDetailService, MaterialService materialService, LocationService locationService, OptionDocumentService optionDocumentService) {
        this.cellerRepository = cellerRepository;
        this.cellerMapper = cellerMapper;
        this.documentService = documentService;
        this.cellerDetailService = cellerDetailService;
        this.materialService = materialService;
        this.locationService = locationService;
        this.optionDocumentService = optionDocumentService;
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
    public CodeDocumentRes findNewCodeDocumentByDocumentCode(Integer code) throws NotFoundException {
        Document document = this.documentService.getDocumentById(code);
        Optional<Celler> celler = this.cellerRepository.findNewCodeDocumentByDocumentCode(document.getName());
        if (celler.isPresent()) {
            String numDocument = celler.get().getNumberDocument();
            int number = Integer.parseInt(numDocument.split("-")[1]);
            return CodeDocumentRes.builder()
                    .document(document.getName())
                    .number(number + 1)
                    .numDocument(document.getName() + "-" + (number + 1))
                    .build();
        } else {
            log.error("Error to findNewCodeDocumentByDocumentCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createCeller(CellerReq celler,String userName) throws InsertException {
        try {
            Celler cellerSave = this.cellerRepository.save(buildCellerToSave(celler, userName));
            this.cellerDetailService.createCellerDetail(celler.getCellerItems(),cellerSave,userName);
        } catch (Exception e) {
            log.error("Error to createCeller: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    private Celler buildCellerToSave(CellerReq celler,String userName) throws NotFoundException {
        OptionDocument reason = this.optionDocumentService.findByCode(celler.getReason());
        Celler cellerNew = new Celler();
        cellerNew.setNumberDocument(celler.getNumberDocument());
        cellerNew.setDate(celler.getDate());
        cellerNew.setDateDocument(celler.getDateDocument());
        cellerNew.setReason(reason.getName());
        cellerNew.setObservation(celler.getObservation());
        cellerNew.setObservations(celler.getObservations());
        cellerNew.setOrigin(celler.getOrigin());
        cellerNew.setDestiny(celler.getDestiny());
        cellerNew.setCreatedBy(userName);
        return cellerNew;
    }

    @Override
    public GenerateReceiptReq getReceipt(String numberDocument, Integer documentId) throws NotFoundException {
        Optional<Celler> celler = this.cellerRepository.findNewCodeDocumentByDocumentCode(numberDocument);
        if (celler.isPresent()) {
            Collection<CellerDetailRes> cellerDetails = this.cellerDetailService.findCellerDetailByCellerCode(celler.get().getId());
            List<GenerateReceiptItemReq> items = new ArrayList<>(0);
            for (CellerDetailRes cellerDetail : cellerDetails) {
                items.add(GenerateReceiptItemReq.builder()
                        .productType(cellerDetail.getMaterial().getTypeMaterial().getName())
                        .productName(cellerDetail.getMaterial().getName())
                        .lot(cellerDetail.getLote())
                        .units(cellerDetail.getAmount())
                        .bags1KG(cellerDetail.getBalance())
                        .bags25KG(cellerDetail.getCoat())
                        .pallets55(cellerDetail.getPallets())
                        .totalWeight(cellerDetail.getWeight())
                        .location(cellerDetail.getLocation().getLocation())
                        .build());
            }
            return GenerateReceiptReq.builder()
                    .receiptNumber(celler.get().getNumberDocument())
                    .receiptDate(celler.get().getDateDocument())
                    .reason(celler.get().getReason())
                    .reasonObservation(celler.get().getObservation())
                    .observations(celler.get().getObservations())
                    .items(items)
                    .build();
        } else {
            log.error("Error to getReceipt {}", numberDocument);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public byte[] generateReceipt(GenerateReceiptReq generateReceiptReq, Integer documentId) throws NotFoundException {
        Document document = this.documentService.getDocumentById(documentId);
        return ReceiptServiceFactory.getService(document.getName()).generateReceipt(generateReceiptReq);
    }

    @Override
    public GenerateReceiptReq buildRecipeReq(CellerReq cellerReq) throws NotFoundException {
        OptionDocument optionDocument = this.optionDocumentService.findByCode(cellerReq.getReason());
        return GenerateReceiptReq.builder()
                .receiptNumber(cellerReq.getNumberDocument())
                .receiptDate(cellerReq.getDateDocument())
                .reason(optionDocument.getName())
                .reasonObservation(cellerReq.getObservation())
                .observations(cellerReq.getObservations())
                .deliveredBy(cellerReq.getDeliveredBy())
                .receivedBy(cellerReq.getReceivedBy())
                .items(buildRecipeItemsReq(cellerReq.getCellerItems()))
                .build();
    }

    private List<GenerateReceiptItemReq> buildRecipeItemsReq(Collection<CellerDetailReq> cellerDetailReq) throws NotFoundException {
        List<GenerateReceiptItemReq> generateReceiptItemReqs = new ArrayList<>(0);
        for (CellerDetailReq celler : cellerDetailReq) {
            Material material = this.materialService.getMaterialByCode(celler.getMaterial());
            Location location = this.locationService.getLocationByCode(celler.getLocation());
            CellerDetail cellerLote = this.cellerDetailService.getCellarDetailByCode(celler.getLote());
            generateReceiptItemReqs.add(GenerateReceiptItemReq.builder()
                    .productType(material.getTypeMaterial().getName())
                    .productName(material.getName())
                    .lot(cellerLote.getLote())
                    .units(celler.getAmount())
                    .bags1KG(celler.getBalance())
                    .bags25KG(celler.getCoat())
                    .pallets55(celler.getPallets())
                    .totalWeight(celler.getWeight())
                    .location(location.getDescription())
                    .build());
        }
        return generateReceiptItemReqs;
    }

}
