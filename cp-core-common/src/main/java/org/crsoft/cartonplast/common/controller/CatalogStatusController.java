package org.crsoft.cartonplast.common.controller;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.service.ICatalogStatusService;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 26/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/status")
public class CatalogStatusController {

    private final ICatalogStatusService catalogStatusService;

    public CatalogStatusController(ICatalogStatusService catalogStatusService) {
        this.catalogStatusService = catalogStatusService;
    }

    @GetMapping("/{type}")
    private ResponseEntity<Collection<CatalogStatusRes>> findAllStatusByType(@PathVariable("type") String type) throws NotFoundException {
        return ResponseEntity.ok().body(this.catalogStatusService.findAllStatusByType(type));
    }
}
