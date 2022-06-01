package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.RelationshipService;
import org.crsoft.cartonplast.users.vo.req.RelationshipReq;
import org.crsoft.cartonplast.users.vo.res.RelationshipRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import java.util.List;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 31/05/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/relationships")
public class RelationshipController {

    private final RelationshipService relationshipService;

    @GetMapping
    public ResponseEntity<List<RelationshipRes>> findAllRelationships() {
        return ResponseEntity.ok(relationshipService.findAllValidRelationShips());
    }

    @PostMapping
    public ResponseEntity<RelationshipRes> createRelationship(
            @Valid @RequestBody RelationshipReq relationshipReq) {
        return ResponseEntity.ok(relationshipService.save(relationshipReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<RelationshipRes> updateRelationship(
            @PathVariable("id") String id,
            @Valid @RequestBody RelationshipReq relationshipReq) {
        return ResponseEntity.ok(relationshipService.update(id, relationshipReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteRelationship(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(relationshipService.delete(id));
    }
}
