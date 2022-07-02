package org.crsoft.cartonplast.catalog.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.catalog.service.ICatalogPriorityService;
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
@RequestMapping(V1_API_VERSION + "/priorities")
@RequiredArgsConstructor
public class CatalogPriorityController {

    private final ICatalogPriorityService catalogPriorityService;

    @GetMapping("/{type}")
    private ResponseEntity<Collection<CatalogPriorityRes>> getAllPrioritiesByType(
            @PathVariable("type") String type) {
        return ResponseEntity.ok().body(this.catalogPriorityService.findAllPriorityByType(type));
    }
}
