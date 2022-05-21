package org.crsoft.cartonplast.config.service.impl;

import io.minio.GetObjectArgs;
import io.minio.MinioClient;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.config.configuration.MinioConfig;
import org.crsoft.cartonplast.config.exception.EmptyFileException;
import org.crsoft.cartonplast.config.exception.FileNotFoundException;
import org.crsoft.cartonplast.config.exception.UploadFailedException;
import org.crsoft.cartonplast.config.service.IMinioService;
import org.crsoft.cartonplast.config.util.FileUtil;
import org.crsoft.cartonplast.config.util.MinioUtil;
import org.crsoft.cartonplast.config.vo.req.FileReq;
import org.crsoft.cartonplast.config.vo.res.FileRes;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;
import java.util.UUID;

/**
 * @author lpillaga on 19/05/2022
 */
@Slf4j
@Service
public class MinioService implements IMinioService {

    private final MinioUtil minioUtil;
    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public MinioService(
            MinioUtil minioUtil,
            MinioClient minioClient,
            MinioConfig minioConfig) {
        this.minioUtil = minioUtil;
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    @Override
    public boolean bucketExists(String bucketName) {
        return minioUtil.bucketExists(bucketName);
    }

    @Override
    public void makeBucket(String bucketName) {
        minioUtil.makeBucket(bucketName);
    }

    @Override
    public boolean removeObject(String bucketName, String objectName) {
        return minioUtil.removeObject(bucketName, objectName);
    }

    @Override
    public String getObjectUrl(String bucketName, String objectName) {
        return minioUtil.getObjectUrl(bucketName, objectName);
    }

    @Override
    public FileRes putObject(FileReq request) {
        MultipartFile file = request.getFile();

        if (request.getFile().isEmpty()) {
            throw new EmptyFileException();
        }

        try {
            String originalFilename = file.getOriginalFilename();
            String generatedFileName = UUID.randomUUID().toString();

            if (originalFilename != null) {
                generatedFileName = this.generateFileName(originalFilename);
            }

            String objectName = this.generateObjectName(request.getDirectory(), generatedFileName);
            String fileType = FileUtil.getFileType(file);

            minioUtil.putObject(
                    request.getBucketName(),
                    file,
                    objectName,
                    fileType);

            return FileRes.builder()
                    .fileName(objectName)
                    .fileType(fileType)
                    .fileUrl(minioConfig.getEndpoint() + "/" + request.getBucketName() + "/" + objectName)
                    .fileSize(file.getSize())
                    .build();

        } catch (Exception e) {
            log.error("Error uploading file to minio", e);
            throw new UploadFailedException();
        }
    }

    @Override
    public InputStream getObject(String bucketName, String fileName) {
        InputStream stream;
        try {
            stream = minioUtil.getObject(bucketName, fileName);
        } catch (Exception e) {
            log.error("Error al obtener el archivo desde minio", e);
            throw new FileNotFoundException();
        }

        return stream;
    }

    private String generateFileName(String fileName) {
        return UUID.randomUUID().toString().replace("-", "") +
                fileName.substring(fileName.lastIndexOf("."));
    }

    private String generateObjectName(List<String> directories, String fileName) {
        StringBuilder sb = new StringBuilder();
        if (directories == null || directories.isEmpty()) {
            return fileName;
        }

        for (String directory : directories) {
            sb.append(directory).append("/");
        }

        sb.append(fileName);
        return sb.toString();
    }
}
