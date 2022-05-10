package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.DieService;
import org.crsoft.cartonplast.design.util.DesignConstant;
import org.crsoft.cartonplast.design.vo.res.DieRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author lpillaga on 30/04/2022
 */

@RestController
@RequestMapping(DesignConstant.V1_API_VERSION + "/dies")
public class DieController {

    private final DieService dieService;

    public DieController(DieService dieService) {
        this.dieService = dieService;
    }

    @GetMapping
    public ResponseEntity<Page<DieRes>> getAllDies(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(required = false) String query) {
        Pageable paging = PageRequest.of(page, size);
        return ResponseEntity.ok(dieService.findAllValidDies(paging, query));
    }
}
