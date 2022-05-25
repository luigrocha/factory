package org.crsoft.cartonplast.config.service;

import org.crsoft.cartonplast.config.vo.req.FileReq;
import org.crsoft.cartonplast.config.vo.res.FileRes;

import java.io.InputStream;

/**
 * @author lpillaga on 19/05/2022
 */
public interface IMinioService {
    boolean bucketExists(String bucketName);
    void makeBucket(String bucketName);

    boolean removeObject(String bucketName, String objectName);

    String getObjectUrl(String bucketName,String objectName);

    FileRes putObject(FileReq request);

    InputStream getObject(String bucketName, String fileName);
}
