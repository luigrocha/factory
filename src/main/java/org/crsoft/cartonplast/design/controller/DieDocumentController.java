package org.crsoft.cartonplast.design.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.vo.res.DieDocumentRes;
import org.crsoft.cartonplast.design.service.impl.DieDocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 20/06/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/dies/{dieId}/documents")
@RequiredArgsConstructor
public class DieDocumentController {

    private final DieDocumentService dieDocumentService;

    @GetMapping
    public ResponseEntity<List<DieDocumentRes>> findAllValidDocumentsByDieId(
            @PathVariable(value = "dieId") Integer dieId) {
        return ResponseEntity.ok(dieDocumentService.findAllValidDocumentsByDieId(dieId));
    }

    @PostMapping
    public ResponseEntity<DieDocumentRes> createDieDocument(
            @PathVariable(value = "dieId") Integer dieId,
            @RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok(dieDocumentService.save(dieId, file));
    }

    @PutMapping("/{id}")
    public ResponseEntity<DieDocumentRes> updateDieDocument(
            @PathVariable(value = "id") Integer id,
            @RequestParam(value = "file") MultipartFile file) {
        return ResponseEntity.ok(dieDocumentService.update(id, file));
    }
}
