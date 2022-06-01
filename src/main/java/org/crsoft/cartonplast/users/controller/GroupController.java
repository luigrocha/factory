package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.GroupService;
import org.crsoft.cartonplast.users.vo.req.GroupReq;
import org.crsoft.cartonplast.users.vo.res.GroupRes;
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
@RequestMapping(V1_API_VERSION + "/groups")
public class GroupController {

    private final GroupService groupService;

    @GetMapping
    public ResponseEntity<List<GroupRes>> findAllGroups() {
        return ResponseEntity.ok(groupService.findAllValidGroups());
    }

    @PostMapping
    public ResponseEntity<GroupRes> createGroup(
            @Valid @RequestBody GroupReq groupReq) {
        return ResponseEntity.ok(groupService.save(groupReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GroupRes> updateGroup(
            @PathVariable("id") String id,
            @Valid @RequestBody GroupReq groupReq) {
        return ResponseEntity.ok(groupService.update(id, groupReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGroup(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(groupService.delete(id));
    }
}
