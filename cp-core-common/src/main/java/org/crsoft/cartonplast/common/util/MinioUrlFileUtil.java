package org.crsoft.cartonplast.common.util;

import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.mapstruct.Named;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lpillaga on 02/06/2022
 */
@Component
public class MinioUrlFileUtil {

    @Value("${cp.config.ws.url}")
    private String wsConfigUrl;

    @Value("${minio.images.bucket-name}")
    private String imagesBucketName;

    @Value("${minio.documents.bucket-name}")
    private String documentsBucketName;

    @Named("getImageUrl")
    public String getImageUrl(String imageName) {
        if ( imageName == null ) {
            return null;
        }

        return wsConfigUrl +
                "/" +
                GlobalConstant.FILES_PATH +
                "/" +
                imagesBucketName +
                "/" +
                imageName;
    }

    @Named("getDocumentUrl")
    public String getDocumentUrl(String documentName) {
        if ( documentName == null ) {
            return null;
        }

        return wsConfigUrl +
                "/" +
                GlobalConstant.FILES_PATH +
                "/" +
                documentsBucketName +
                "/" +
                documentName;
    }
}
