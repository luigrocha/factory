package org.crsoft.cartonplast.common.controller;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.service.ICatalogPriorityService;
import org.crsoft.cartonplast.vo.res.CatalogPriorityRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 25/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/priority")
public class CatalogPriorityController {

    private final ICatalogPriorityService catalogPriorityService;

    public CatalogPriorityController(ICatalogPriorityService catalogPriorityService) {
        this.catalogPriorityService = catalogPriorityService;
    }

    @GetMapping("/{type}")
    private ResponseEntity<Collection<CatalogPriorityRes>> getAllPrioritiesByType(@PathVariable("type") String type) {
        try {
            return ResponseEntity.ok().body(this.catalogPriorityService.findAllPriorityByType(type));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
