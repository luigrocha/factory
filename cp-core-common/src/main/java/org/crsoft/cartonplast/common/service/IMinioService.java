package org.crsoft.cartonplast.common.service;

import org.springframework.web.multipart.MultipartFile;

public interface IMinioService {
    boolean bucketExists(String bucketName);
    void makeBucket(String bucketName);
    boolean removeBucket(String bucketName);
    String putObject(MultipartFile file, String bucketName, String fileType);
}
