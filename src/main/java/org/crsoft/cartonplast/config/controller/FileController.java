package org.crsoft.cartonplast.config.controller;

import cn.hutool.core.text.AntPathMatcher;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.compress.utils.IOUtils;
import org.crsoft.cartonplast.config.service.impl.MinioService;
import org.crsoft.cartonplast.config.util.FileUtil;
import org.crsoft.cartonplast.config.vo.req.FileReq;
import org.crsoft.cartonplast.config.vo.res.FileRes;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import java.io.IOException;
import java.io.InputStream;

import static org.crsoft.cartonplast.config.constant.GlobalConstant.V1_API_VERSION;
import static org.springframework.web.servlet.HandlerMapping.BEST_MATCHING_PATTERN_ATTRIBUTE;

/**
 * @author lpillaga on 19/05/2022
 */

@Slf4j
@RestController
@RequestMapping(V1_API_VERSION + "/files")
public class FileController {

    private final MinioService minioService;

    public FileController(MinioService minioService) {
        this.minioService = minioService;
    }

    @PostMapping("/upload")
    public ResponseEntity<FileRes> uploadFile(
            @Valid FileReq fileReq) {
        log.info("FileController.uploadFile()");
        return ResponseEntity.ok(this.minioService.putObject(fileReq));
    }

    @GetMapping(value = "/{bucketName}/**")
    public ResponseEntity<Object> getFile(
            @PathVariable(value = "bucketName") String bucketName,
            HttpServletRequest request) throws IOException {
        String pattern = (String) request.getAttribute(BEST_MATCHING_PATTERN_ATTRIBUTE);
        String fileName = new AntPathMatcher().extractPathWithinPattern(pattern, request.getServletPath());
        InputStream inputStream = this.minioService.getObject(bucketName, fileName);
        MediaType contentType = FileUtil.getMediaType(fileName);
        return ResponseEntity.ok()
                .contentType(contentType)
                .body(IOUtils.toByteArray(inputStream));
    }

}
