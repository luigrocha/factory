package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.Document;
import org.crsoft.cartonplast.celler.repository.CellerRepository;
import org.crsoft.cartonplast.celler.service.ICellerService;
import org.crsoft.cartonplast.celler.service.mapper.CellerMapper;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptItemReq;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
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

    public CellerService(CellerRepository cellerRepository, CellerMapper cellerMapper,
                         DocumentService documentService,@Lazy CellerDetailService cellerDetailService) {
        this.cellerRepository = cellerRepository;
        this.cellerMapper = cellerMapper;
        this.documentService = documentService;
        this.cellerDetailService = cellerDetailService;
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
    public void createCeller(Celler celler) throws InsertException {
        try {
            this.cellerRepository.save(celler);
        } catch (Exception e) {
            log.error("Error to createCeller: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
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

}
