package org.crsoft.cartonplast.config.util;

import io.minio.*;
import io.minio.http.Method;
import io.minio.messages.Bucket;
import io.minio.messages.Item;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.config.configuration.MinioConfig;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author lpillaga on 19/05/2022
 */
@Slf4j
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

        boolean isCreated = makeBucket(bucketName);

        if (isCreated) {
            log.info("Bucket {} created", bucketName);
        } else {
            log.info("Bucket {} already exists", bucketName);
        }

        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket(bucketName)
                        .object(objectName)
                        .stream(inputStream, file.getSize(), -1)
                        .contentType(objectType).build()
        );
    }

    @SneakyThrows
    public boolean bucketExists(String bucketName) {
        boolean found = minioClient
                .bucketExists(BucketExistsArgs.builder()
                        .bucket(bucketName)
                        .build());
        if (found) {
            log.info("Bucket {} found", bucketName);
        } else {
            log.info("Bucket {} not found", bucketName);
        }
        return found;
    }

    @SneakyThrows
    public boolean makeBucket(String bucketName) {
        boolean exists = bucketExists(bucketName);
        if (!exists) {
            minioClient.makeBucket(
                    MakeBucketArgs.builder()
                            .bucket(bucketName)
                            .build());
            return true;
        } else {
            return false;
        }
    }

    @SneakyThrows
    public List<String> listBucketNames() {
        List<Bucket> bucketList = listBuckets();
        List<String> bucketListName = new ArrayList<>();
        for (Bucket bucket : bucketList) {
            bucketListName.add(bucket.name());
        }
        return bucketListName;
    }

    @SneakyThrows
    public List<Bucket> listBuckets() {
        return minioClient.listBuckets();
    }

    @SneakyThrows
    public List<String> listObjectNames(String bucketName) {
        List<String> listObjectNames = new ArrayList<>();
        boolean flag = bucketExists(bucketName);
        if (flag) {
            Iterable<Result<Item>> myObjects = listObjects(bucketName);
            for (Result<Item> result : myObjects) {
                Item item = result.get();
                listObjectNames.add(item.objectName());
            }
        } else {
            listObjectNames.add(" Bucket does not exist ");
        }
        return listObjectNames;
    }

    @SneakyThrows
    public Iterable<Result<Item>> listObjects(String bucketName) {
        boolean exists = bucketExists(bucketName);
        if (exists) {
            return minioClient.listObjects(
                    ListObjectsArgs.builder().bucket(bucketName).build());
        }
        return null;
    }

    @SneakyThrows
    public String getObjectUrl(String bucketName, String objectName) {
        boolean exists = bucketExists(bucketName);
        String url = "";
        if (exists) {
            url = minioClient.getPresignedObjectUrl(
                    GetPresignedObjectUrlArgs.builder()
                            .method(Method.GET)
                            .bucket(bucketName)
                            .object(objectName)
                            .expiry(2, TimeUnit.MINUTES)
                            .build());
            log.info("Object URL: {}", url);
        }
        return url;
    }

    @SneakyThrows
    public boolean removeObject(String bucketName, String objectName) {
        boolean flag = bucketExists(bucketName);
        if (flag) {
            minioClient.removeObject(
                    RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
            return true;
        }
        return false;
    }

    @SneakyThrows
    public InputStream getObject(String bucketName, String objectName) {
        boolean exists = bucketExists(bucketName);
        if (exists) {
            return minioClient.getObject(GetObjectArgs.builder()
                    .bucket(bucketName)
                    .object(objectName)
                    .build());
        }
        return null;
    }
}
