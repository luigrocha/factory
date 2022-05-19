package org.crsoft.cartonplast.common.service.impl;

import io.minio.MinioClient;
import org.crsoft.cartonplast.common.config.MinioConfig;
import org.crsoft.cartonplast.common.service.IMinioService;
import org.crsoft.cartonplast.common.util.MinioUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

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
        return false;
    }

    @Override
    public void makeBucket(String bucketName) {

    }

    @Override
    public boolean removeBucket(String bucketName) {
        return false;
    }

    @Override
    public String putObject(MultipartFile file, String bucketName, String fileType) {
        try {
            bucketName = minioConfig.getBucketName();
            String fileName = file.getOriginalFilename();
            String objectName = UUID.randomUUID().toString().replace("-", "") +
                    fileName.substring(fileName.lastIndexOf("."));
            minioUtil.putObject(
                    bucketName,
                    file,
                    objectName,
                    fileType);

            return minioConfig.getEndpoint() + "/" + bucketName + "/" + objectName;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }
}
