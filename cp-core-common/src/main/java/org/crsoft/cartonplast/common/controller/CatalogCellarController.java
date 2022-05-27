package org.crsoft.cartonplast.common.controller;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.service.ICatalogCellarService;
import org.crsoft.cartonplast.vo.res.CatalogCellarRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 27/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/catalogCellar")
public class CatalogCellarController {

    private final ICatalogCellarService catalogCellarService;

    public CatalogCellarController(ICatalogCellarService catalogCellarService) {
        this.catalogCellarService = catalogCellarService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<Collection<CatalogCellarRes>> findAllCatalogCellarByType(@PathVariable("id") Integer id) {
        try {
            return ResponseEntity.ok().body(this.catalogCellarService.findAllCatalogCellarByType(id));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
