package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.GenderService;
import org.crsoft.cartonplast.users.vo.req.GenderReq;
import org.crsoft.cartonplast.users.vo.res.GenderRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 30/05/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/genders")
public class GenderController {

    private final GenderService genderService;

    @GetMapping
    public ResponseEntity<List<GenderRes>> findAllGenders() {
        return ResponseEntity.ok(genderService.findAllValidGenders());
    }

    @PostMapping
    public ResponseEntity<GenderRes> createGender(
            @Valid @RequestBody GenderReq genderReq) {
        return ResponseEntity.ok(genderService.save(genderReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<GenderRes> updateGender(
            @PathVariable("id") String id,
            @Valid @RequestBody GenderReq genderReq) {
        return ResponseEntity.ok(genderService.update(id, genderReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteGender(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(genderService.delete(id));
    }
}
