package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.client.MinioClient;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.constant.LogMessageConstant;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.design.model.Die;
import org.crsoft.cartonplast.design.model.DieDocument;
import org.crsoft.cartonplast.design.repository.DieDocumentRepository;
import org.crsoft.cartonplast.design.repository.DieRepository;
import org.crsoft.cartonplast.design.service.IDieDocumentService;
import org.crsoft.cartonplast.design.service.mapper.DieDocumentMapper;
import org.crsoft.cartonplast.design.vo.res.DieDocumentRes;
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
public class DieDocumentService implements IDieDocumentService {

    private final DieDocumentRepository dieDocumentRepository;
    private final DieRepository dieRepository;
    private final DieDocumentMapper dieDocumentMapper;
    private final MinioClient minioClient;

    @Value("${minio.documents.bucket-name}")
    private String documentsBucketName;

    @Override
    public List<DieDocumentRes> findAllValidDocumentsByDieId(Integer dieId) {
        return dieDocumentMapper.toDieDocumentResList(
                dieDocumentRepository.findAllValidDocumentsByDieId(dieId)
        );
    }

    @Override
    public DieDocumentRes save(Integer dieId, MultipartFile file) {
        Optional<Die> dieOptional = dieRepository.findById(dieId);

        if (dieOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieId);
            throw new NotExistException(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieId);
        }

        if (file.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FILE_EMPTY_MESSAGE + file.getOriginalFilename());
            throw new NotExistException("El archivo está vacio: " + file.getOriginalFilename());
        }

        UploadFileReq uploadFileReq = UploadFileReq.builder()
                .name(file.getOriginalFilename())
                .bucketName(this.documentsBucketName)
                .directory(GlobalConstant.DIE_DOCUMENTS_DIRECTORY)
                .file(file)
                .build();
        UploadFileRes uploadFileRes = minioClient.uploadFile(uploadFileReq);
        String documentName = uploadFileRes.getFileName();

        int maxVersionNumber = dieDocumentRepository.findLastVersionNumberByDieId(dieId);
        DieDocument dieDocument = DieDocument.builder()
                .version(maxVersionNumber + 1)
                .name(documentName)
                .die(dieOptional.get())
                .build();

        return dieDocumentMapper.toDieDocumentRes(dieDocumentRepository.save(dieDocument));
    }

    @Override
    public DieDocumentRes update(Integer id, MultipartFile file) {
        Optional<DieDocument> dieDocumentOptional = dieDocumentRepository.findById(id);

        if (dieDocumentOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + id);
            throw new NotExistException(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + id);
        }

        if (file.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FILE_EMPTY_MESSAGE + file.getOriginalFilename());
            throw new NotExistException("El archivo está vacio: " + file.getOriginalFilename());
        }

        DieDocument dieDocument = dieDocumentOptional.get();

        UpdateFileReq updateFileReq = UpdateFileReq.builder()
                .actualName(dieDocument.getName())
                .name(file.getOriginalFilename())
                .bucketName(this.documentsBucketName)
                .directory(GlobalConstant.DIE_DOCUMENTS_DIRECTORY)
                .file(file)
                .build();
        UploadFileRes uploadFileRes = minioClient.updateFile(updateFileReq);
        String documentName = uploadFileRes.getFileName();

        dieDocument.setName(documentName);
        dieDocument.setVersionDate(LocalDateTime.now());

        return dieDocumentMapper.toDieDocumentRes(dieDocumentRepository.save(dieDocument));
    }
}
