package org.crsoft.cartonplast.client.controller;

import org.crsoft.cartonplast.client.service.impl.ClientService;
import org.crsoft.cartonplast.common.service.impl.MinioService;
import org.crsoft.cartonplast.common.util.FileUtil;
import org.crsoft.cartonplast.vo.res.ClientRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

@RestController
@RequestMapping(V1_API_VERSION + "/clients")
public class ClientController {

    private final ClientService clientService;
    private final MinioService minioService;

    public ClientController(ClientService clientService, MinioService minioService) {
        this.clientService = clientService;
        this.minioService = minioService;
    }

    @GetMapping
    public ResponseEntity<List<ClientRes>> getAllClients() {
        return ResponseEntity.ok(this.clientService.findAllValidClients());
    }

    @PostMapping
    public String createClient(
            MultipartFile file) {
        String fileType = FileUtil.getFileType(file);
        if (fileType != null) {
            return minioService.putObject(file, "images", fileType);
        }
        return "";
    }
}
