package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.CyrelService;
import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 12/05/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/cyrels")
public class CyrelController {

    private final CyrelService cyrelService;

    public CyrelController(CyrelService cyrelService) {
        this.cyrelService = cyrelService;
    }

    @GetMapping
    public ResponseEntity<Page<CyrelRes>> getAllCyrels(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(this.cyrelService.findAllValidCyrels(paging, query));
    }
}
