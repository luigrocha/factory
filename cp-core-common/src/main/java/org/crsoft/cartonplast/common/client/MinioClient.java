package org.crsoft.cartonplast.common.client;

import org.crsoft.cartonplast.vo.req.UpdateFileReq;
import org.crsoft.cartonplast.vo.req.UploadFileReq;
import org.crsoft.cartonplast.vo.res.UploadFileRes;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

/**
 * @author lpillaga on 20/05/2022
 */
@FeignClient(name = "cp-config-ws", url = "${cp.config.ws.url}")
public interface MinioClient {

    @PostMapping(value = "/files/upload", consumes = "multipart/form-data")
    UploadFileRes uploadFile(UploadFileReq body);

    @PutMapping(value = "/files/update", consumes = "multipart/form-data")
    UploadFileRes updateFile(UpdateFileReq body);
}
