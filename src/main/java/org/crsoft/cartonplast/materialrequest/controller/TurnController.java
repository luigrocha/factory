package org.crsoft.cartonplast.materialrequest.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.materialrequest.service.ITurnService;
import org.crsoft.cartonplast.vo.req.TurnReq;
import org.crsoft.cartonplast.vo.res.TurnRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 15/07/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/turns")
@RequiredArgsConstructor
public class TurnController {

    private final ITurnService turnService;

    @GetMapping
    public ResponseEntity<List<TurnRes>> findAllValidTurns(
            @RequestParam(name = "isActive", required = false) Boolean isActive) {
        return ResponseEntity.ok(turnService.findAllValidTurns(isActive));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TurnRes> updateTurn(
            @PathVariable("id") Integer id,
            @Valid @RequestBody TurnReq turnReq) {
        return ResponseEntity.ok(turnService.updateTurn(id, turnReq));
    }
}
