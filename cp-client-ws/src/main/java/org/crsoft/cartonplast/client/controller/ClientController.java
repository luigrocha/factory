package org.crsoft.cartonplast.client.controller;

import org.crsoft.cartonplast.client.service.impl.ClientService;
import org.crsoft.cartonplast.client.vo.req.CreateClientReq;
import org.crsoft.cartonplast.vo.res.ClientRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

@RestController
@RequestMapping(V1_API_VERSION + "/clients")
public class ClientController {

    private final ClientService clientService;

    public ClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    @GetMapping
    public ResponseEntity<List<ClientRes>> getAllClients() {
        return ResponseEntity.ok(this.clientService.findAllValidClients());
    }

    @PostMapping(consumes = { "multipart/form-data" })
    public ResponseEntity<ClientRes> createClient(
            @RequestParam("id") String id,
            @RequestParam("name") String name,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "categoryId", required = false) Integer categoryId) {
        CreateClientReq createClientReq = CreateClientReq.builder()
                .id(id)
                .name(name)
                .categoryId(categoryId)
                .logo(file)
                .build();
        return ResponseEntity.ok(this.clientService.saveClient(createClientReq));
    }
}
