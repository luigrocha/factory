package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.service.IOptionDocumentService;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.OptionDocumentRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

/**
 * @author jyepez on 2/6/2022
 */
@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/optionDocument")
public class OptionDocumentController {

    private final IOptionDocumentService optionDocumentService;

    public OptionDocumentController(IOptionDocumentService optionDocumentService) {
        this.optionDocumentService = optionDocumentService;
    }

    @GetMapping("/{code}")
    public ResponseEntity<Collection<OptionDocumentRes>> findAllByDocumentCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok(this.optionDocumentService.findAllByDocumentCode(code));
    }
}
