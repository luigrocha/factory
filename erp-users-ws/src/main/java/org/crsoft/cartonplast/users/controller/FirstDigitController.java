package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.FirstDigitService;
import org.crsoft.cartonplast.users.vo.req.FirstDigitReq;
import org.crsoft.cartonplast.users.vo.res.FirstDigitRes;
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
@RequestMapping(V1_API_VERSION + "/first-digits")
public class FirstDigitController {

    private final FirstDigitService firstDigitService;

    @GetMapping
    public ResponseEntity<List<FirstDigitRes>> findAllFirstDigits() {
        return ResponseEntity.ok(firstDigitService.findAllValidFirstDigits());
    }

    @PostMapping
    public ResponseEntity<FirstDigitRes> createFirstDigit(
            @Valid @RequestBody FirstDigitReq firstDigitReq) {
        return ResponseEntity.ok(firstDigitService.save(firstDigitReq));
    }

    @PutMapping("/{id}")
    public ResponseEntity<FirstDigitRes> updateFirstDigit(
            @PathVariable("id") String id,
            @Valid @RequestBody FirstDigitReq firstDigitReq) {
        return ResponseEntity.ok(firstDigitService.update(id, firstDigitReq));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deleteFirstDigit(
            @PathVariable("id") String id) {
        return ResponseEntity.ok(firstDigitService.delete(id));
    }
}
