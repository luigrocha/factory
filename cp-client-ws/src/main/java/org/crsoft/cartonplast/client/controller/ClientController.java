package org.crsoft.cartonplast.client.controller;

import org.crsoft.cartonplast.client.service.impl.ClientService;
import org.crsoft.cartonplast.client.vo.res.ClientRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
