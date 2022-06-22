package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.design.service.impl.CyrelDocumentService;
import org.crsoft.cartonplast.design.vo.res.CyrelDocumentRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 20/06/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/cyrels/{cyrelId}/documents")
@RequiredArgsConstructor
public class CyrelDocumentController {

    private final CyrelDocumentService cyrelDocumentService;

    @GetMapping
    public ResponseEntity<List<CyrelDocumentRes>> findAllValidDocumentsByCyrelId(
            @PathVariable(value = "cyrelId") Integer cyrelId) {
        return ResponseEntity.ok(cyrelDocumentService.findAllValidDocumentsByCyrelId(cyrelId));
    }

    @PostMapping
    public ResponseEntity<CyrelDocumentRes> createCyrelDocument(
            @PathVariable(value = "cyrelId") Integer cyrelId,
            @RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok(cyrelDocumentService.save(cyrelId, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<CyrelDocumentRes> updateCyrelDocument(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok(cyrelDocumentService.update(id, file));
    }
}
