package org.crsoft.cartonplast.common.util;

import io.minio.MinioClient;
import io.minio.PutObjectArgs;
import lombok.SneakyThrows;
import org.crsoft.cartonplast.common.config.MinioConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;

@Component
public class MinioUtil {

    private final MinioClient minioClient;
    private final MinioConfig minioConfig;

    public MinioUtil(MinioClient minioClient, MinioConfig minioConfig) {
        this.minioClient = minioClient;
        this.minioConfig = minioConfig;
    }

    @SneakyThrows
    public void putObject(
            String bucketName,
            MultipartFile file,
            String objectName,
            String objectType) {
        InputStream inputStream = new ByteArrayInputStream(file.getBytes());
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(objectType).build()
        );
    }
}
