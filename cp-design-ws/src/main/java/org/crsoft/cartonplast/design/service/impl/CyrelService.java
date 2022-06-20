package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.client.MinioClient;
import org.crsoft.cartonplast.common.constant.CatalogStatusConstant;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.constant.LogMessageConstant;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.crsoft.cartonplast.common.service.impl.CatalogStatusService;
import org.crsoft.cartonplast.design.model.*;
import org.crsoft.cartonplast.design.model.pk.CyrelDieProductId;
import org.crsoft.cartonplast.design.repository.*;
import org.crsoft.cartonplast.design.service.ICyrelService;
import org.crsoft.cartonplast.design.service.mapper.CyrelMapper;
import org.crsoft.cartonplast.design.vo.req.CyrelReq;
import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.crsoft.cartonplast.vo.req.UploadFileReq;
import org.crsoft.cartonplast.vo.res.UploadFileRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;


/**
 * @author lpillaga on 12/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CyrelService implements ICyrelService {

    private final CyrelRepository cyrelRepository;
    private final CyrelMapper cyrelMapper;
    private final CatalogStatusService catalogStatusService;
    private final PrinterRepository printerRepository;
    private final ColorBRepository colorBRepository;
    private final DieProductRepository dieProductRepository;
    private final ColorCatalogRepository colorCatalogRepository;
    private final ColorTypeRepository colorTypeRepository;
    private final MinioClient minioClient;


    @Value("${minio.documents.bucket-name}")
    private String documentsBucketName;

    @Override
    public Page<CyrelRes> findAllValidCyrels(Pageable pageable, String query) {
        Page<Cyrel> cyrels = cyrelRepository.findAllValidCyrels(pageable, query);
        return cyrels.map(cyrelMapper::cyrelToCyrelRes);
    }

    @Override
    @Transactional
    public CyrelRes createCyrel(CyrelReq cyrelReq) {
        Optional<CatalogStatus> catalogStatusOptional = catalogStatusService.findByTypeAndIsDefault(CatalogStatusConstant.CYREL_STATUS_CODE);

        if (catalogStatusOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + cyrelReq);
            throw new NotExistException("No existe un estado por defecto para cireles");
        }

        Printer printer = printerRepository
                .findById(cyrelReq.getPrinterId())
                .orElseThrow(() -> {
                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + cyrelReq);
                    return new NotExistException("No existe una impresora con el id: " + cyrelReq.getPrinterId());
                });

        ColorB colorB = colorBRepository
                .findById(cyrelReq.getMbLeafId())
                .orElse(null);

        Cyrel cyrel = cyrelMapper.cyrelReqToCyrel(cyrelReq);
        cyrel.setStatus(catalogStatusOptional.get());
        cyrel.setPrinter(printer);
        cyrel.setMbLeaf(colorB);

        List<DieProduct> dieProducts = dieProductRepository.findAllById(cyrelReq.getDieProductIds());
        List<CyrelDieProduct> cyrelDieProducts = dieProducts
                .stream()
                .map(dieProduct -> CyrelDieProduct.builder()
                        .cyrelDieProductId(new CyrelDieProductId(dieProduct.getId(), null))
                        .cyrel(cyrel)
                        .dieProduct(dieProduct)
                        .build()).collect(Collectors.toList());
        cyrel.setDies(cyrelDieProducts);
        List<CyrelColor> colors = cyrelReq.getCyrelColors().stream()
                .map(cyrelColor -> CyrelColor.builder()
                        .index(cyrelColor.getIndex())
                        .observation(cyrelColor.getObservation())
                        .cyrel(cyrel)
                        .color(colorCatalogRepository.findById(cyrelColor.getColor().getId()).orElseThrow(
                                () -> {
                                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + cyrelReq);
                                    return new NotExistException("No existe un color con el id: " + cyrelColor.getColor().getId());
                                }
                        ))
                        .colorType(colorTypeRepository.findById(cyrelColor.getColorType()).orElseThrow(
                                () -> {
                                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + cyrelReq);
                                    return new NotExistException("No existe un tipo de color con el id: " + cyrelColor.getColorType());
                                }
                        ))
                        .build()
                ).collect(Collectors.toList());
        cyrel.setCyrelColors(colors);

        return cyrelMapper.cyrelToCyrelRes(cyrelRepository.save(cyrel));
    }

    @Override
    public CyrelRes uploadCyrelDocument(Integer id, MultipartFile file) {
        Optional<Cyrel> cyrelOptional = cyrelRepository.findById(id);

        if (cyrelOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + id);
            throw new NotExistException("No existe un cirel con el id: " + id);
        }

        if (file.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FILE_EMPTY_MESSAGE + file.getOriginalFilename());
            throw new NotExistException("El archivo es vacio: " + file.getOriginalFilename());
        }

        UploadFileReq uploadFileReq = UploadFileReq.builder()
                .name(file.getOriginalFilename())
                .bucketName(this.documentsBucketName)
                .directory(GlobalConstant.CYREL_DOCUMENTS_DIRECTORY)
                .file(file)
                .build();
        UploadFileRes uploadFileRes = minioClient.uploadFile(uploadFileReq);
        String documentName = uploadFileRes.getFileName();

        Cyrel cyrel = cyrelOptional.get();
        cyrel.setDocumentName(documentName);

        return cyrelMapper.cyrelToCyrelRes(cyrelRepository.save(cyrel));
    }
}
