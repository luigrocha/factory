package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.client.MinioClient;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.constant.LogMessageConstant;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.design.service.ICyrelDocumentService;
import org.crsoft.cartonplast.design.model.Cyrel;
import org.crsoft.cartonplast.design.model.CyrelDocument;
import org.crsoft.cartonplast.design.repository.CyrelDocumentRepository;
import org.crsoft.cartonplast.design.repository.CyrelRepository;
import org.crsoft.cartonplast.design.service.mapper.CyrelDocumentMapper;
import org.crsoft.cartonplast.vo.res.CyrelDocumentRes;
import org.crsoft.cartonplast.vo.req.UpdateFileReq;
import org.crsoft.cartonplast.vo.req.UploadFileReq;
import org.crsoft.cartonplast.vo.res.UploadFileRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 20/06/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class CyrelDocumentService implements ICyrelDocumentService {

    private final CyrelDocumentRepository cyrelDocumentRepository;
    private final CyrelRepository cyrelRepository;
    private final CyrelDocumentMapper cyrelDocumentMapper;
    private final MinioClient minioClient;

    @Value("${minio.documents.bucket-name}")
    private String documentsBucketName;

    @Override
    public List<CyrelDocumentRes> findAllValidDocumentsByCyrelId(Integer cyrelId) {
        return cyrelDocumentMapper.toCyrelDocumentResList(
                cyrelDocumentRepository.findAllValidDocumentsByCyrelId(cyrelId)
        );
    }

    @Override
    public CyrelDocumentRes save(Integer cyrelId, MultipartFile file) {
        Optional<Cyrel> cyrelOptional = cyrelRepository.findById(cyrelId);

        if (cyrelOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + cyrelId);
            throw new NotExistException(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + cyrelId);
        }

        if (file.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FILE_EMPTY_MESSAGE + file.getOriginalFilename());
            throw new NotExistException("El archivo está vacio: " + file.getOriginalFilename());
        }

        UploadFileReq uploadFileReq = UploadFileReq.builder()
                .name(file.getOriginalFilename())
                .bucketName(this.documentsBucketName)
                .directory(GlobalConstant.CYREL_DOCUMENTS_DIRECTORY)
                .file(file)
                .build();
        UploadFileRes uploadFileRes = minioClient.uploadFile(uploadFileReq);
        String documentName = uploadFileRes.getFileName();

        int maxVersionNumber = cyrelDocumentRepository.findLastVersionNumberByCyrelId(cyrelId);
        CyrelDocument cyrelDocument = CyrelDocument.builder()
                .version(maxVersionNumber + 1)
                .name(documentName)
                .cyrel(cyrelOptional.get())
                .build();

        return cyrelDocumentMapper.toCyrelDocumentRes(cyrelDocumentRepository.save(cyrelDocument));
    }

    @Override
    public CyrelDocumentRes update(Integer id, MultipartFile file) {
        Optional<CyrelDocument> cyrelDocumentOptional = cyrelDocumentRepository.findById(id);

        if (cyrelDocumentOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + id);
            throw new NotExistException(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + id);
        }

        if (file.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FILE_EMPTY_MESSAGE + file.getOriginalFilename());
            throw new NotExistException("El archivo está vacio: " + file.getOriginalFilename());
        }

        CyrelDocument cyrelDocument = cyrelDocumentOptional.get();

        UpdateFileReq updateFileReq = UpdateFileReq.builder()
                .actualName(cyrelDocument.getName())
                .name(file.getOriginalFilename())
                .bucketName(this.documentsBucketName)
                .directory(GlobalConstant.CYREL_DOCUMENTS_DIRECTORY)
                .file(file)
                .build();
        UploadFileRes uploadFileRes = minioClient.updateFile(updateFileReq);
        String documentName = uploadFileRes.getFileName();

        cyrelDocument.setName(documentName);
        cyrelDocument.setVersionDate(LocalDateTime.now());

        return cyrelDocumentMapper.toCyrelDocumentRes(cyrelDocumentRepository.save(cyrelDocument));
    }
}
