package org.crsoft.cartonplast.celler.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.Document;
import org.crsoft.cartonplast.celler.model.Location;
import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.celler.repository.CellerRepository;
import org.crsoft.cartonplast.celler.repository.DocumentRepository;
import org.crsoft.cartonplast.celler.service.ICellerService;
import org.crsoft.cartonplast.celler.service.mapper.CellerMapper;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.crsoft.cartonplast.vo.res.CodeDocumentRes;
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
@RequiredArgsConstructor
public class CellerService implements ICellerService {

    private static final String TABLE_NAME = "CATCELL";
    private final CellerRepository cellerRepository;
    private final CellerMapper cellerMapper;
    private final MaterialService materialService;
    private final DocumentService documentService;
    private final LocationService locationService;
    private final DocumentRepository documentRepository;

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

    @Override
    public CodeDocumentRes findNewCodeDocumentByDocumentCode(Integer code) throws NotFoundException {
        Document document = this.documentService.getDocumentById(code);
        Optional<Celler> celler = this.cellerRepository.findDistinctTopByDocumentAndValidToIsNullOrderByCreatedAtDesc(document);
        if (celler.isPresent()) {
            String numDocument = celler.get().getNumberDocument();
            int number = Integer.parseInt(numDocument.split("-")[1]);
            return CodeDocumentRes.builder()
                    .document(document.getName())
                    .number(number)
                    .numDocument(document.getName() + "-" + (number + 1))
                    .build();
        } else {
            log.error("Error to findNewCodeDocumentByDocumentCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createCeller(Collection<Celler> cellers) throws NotFoundException, InsertException {
        Collection<Celler> cellersSave = new ArrayList<>(0);
        for (Celler celler : cellers) {
            Material material = this.materialService.getMaterialByCode(celler.getMaterial().getId());
            Location location = this.locationService.getLocationByCode(celler.getLocation().getId());
            Document document = this.documentService.getDocumentById(celler.getDocument().getId());
            celler.setMaterial(material);
            celler.setLocation(location);
            celler.setDocument(document);
            cellersSave.add(celler);
        }
        try {
            this.cellerRepository.saveAll(cellersSave);
        } catch (Exception e) {
            log.error("Error to createCeller: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public byte[] generateReceipt(GenerateReceiptReq generateReceiptReq, Integer documentId) {
        Optional<Document> optionalDocument = this.documentRepository.findById(documentId);

        if (optionalDocument.isEmpty()) {
            log.error("Error to generateReceipt: Document not found");
            throw new NotExistException("No se pudo generar el comprobante, el tipo de documento no existe");
        }

        Document document = optionalDocument.get();
        return ReceiptServiceFactory.getService(document.getName()).generateReceipt(generateReceiptReq);
    }

}
