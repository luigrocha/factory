package org.crsoft.cartonplast.users.util;

import org.crsoft.cartonplast.users.constant.GlobalConstant;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author lpillaga on 31/05/2022
 */
@Component
public class MinioImageUtil {

    @Value("${cp.config.ws.url}")
    private String wsConfigUrl;

    @Value("${minio.images.bucket-name}")
    private String imagesBucketName;

    public String getImageUrl(String imageName) {
        return wsConfigUrl +
                "/" +
                GlobalConstant.FILES_PATH +
                "/" +
                imagesBucketName +
                "/" +
                imageName;
    }
}
