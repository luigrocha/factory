package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.SecondDigitService;
import org.crsoft.cartonplast.users.vo.req.SecondDigitReq;
import org.crsoft.cartonplast.users.vo.res.SecondDigitRes;
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
@RequestMapping(V1_API_VERSION + "/second-digits")
public class SecondDigitController {

    private final SecondDigitService secondDigitService;

    @GetMapping
    public ResponseEntity<List<SecondDigitRes>> findAllSecondDigits() {
        return ResponseEntity.ok(secondDigitService.findAllValidSecondDigits());
    }

    @PostMapping
    public ResponseEntity<SecondDigitRes> createSecondDigit(
            @Valid @RequestBody SecondDigitReq secondDigitReq) {
        return ResponseEntity.ok(secondDigitService.save(secondDigitReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<SecondDigitRes> updateSecondDigit(
            @PathVariable("id") String id,
            @Valid @RequestBody SecondDigitReq secondDigitReq) {
        return ResponseEntity.ok(secondDigitService.update(id, secondDigitReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteSecondDigit(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(secondDigitService.delete(id));
    }
}
