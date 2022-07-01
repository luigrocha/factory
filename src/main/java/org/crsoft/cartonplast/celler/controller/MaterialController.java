package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.service.IMaterialService;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.MaterialRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/material")
public class MaterialController {

    private final IMaterialService catalogCellarService;

    public MaterialController(IMaterialService catalogCellarService) {
        this.catalogCellarService = catalogCellarService;
    }

    @GetMapping("/{id}")
    private ResponseEntity<Collection<MaterialRes>> findAllCatalogCellarByType(@PathVariable("id") Integer id) throws NotFoundException {
        return ResponseEntity.ok().body(this.catalogCellarService.findAllCatalogCellarByType(id));
    }

/*    @GetMapping("/findIfExistStock/{id}")
    private ResponseEntity<Collection<MaterialRes>> findIfExistStock(@PathVariable("id") Integer id){
        return ResponseEntity.ok().body(this.catalogCellarService.findIfExistStock(id));
    }*/
}
