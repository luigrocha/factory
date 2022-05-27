package org.crsoft.cartonplast.common.controller;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.service.ITypeCellarService;
import org.crsoft.cartonplast.vo.res.TypeCellarRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author jyepez on 27/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/typeCellar")
public class TypeCellarController {

    private final ITypeCellarService typeCellarService;

    public TypeCellarController(ITypeCellarService typeCellarService) {
        this.typeCellarService = typeCellarService;
    }

    @GetMapping
    private ResponseEntity<Collection<TypeCellarRes>> findAllTypeCellar() {
        try {
            return ResponseEntity.ok().body(this.typeCellarService.findAllTypeCellar());
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }
}
