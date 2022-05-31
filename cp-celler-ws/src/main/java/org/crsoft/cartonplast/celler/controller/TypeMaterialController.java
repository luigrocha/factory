package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.service.ITypeMaterialService;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.TypeMaterialRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/typeMaterial")
public class TypeMaterialController {

    private final ITypeMaterialService typeCellarService;

    public TypeMaterialController(ITypeMaterialService typeCellarService) {
        this.typeCellarService = typeCellarService;
    }

    @GetMapping
    private ResponseEntity<Collection<TypeMaterialRes>> findAllTypeCellar() throws NotFoundException {
            return ResponseEntity.ok().body(this.typeCellarService.findAllTypeCellar());
    }
}
