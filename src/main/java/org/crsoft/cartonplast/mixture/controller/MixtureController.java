package org.crsoft.cartonplast.mixture.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.mixture.service.IMixtureService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jyepez on 3/7/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(GlobalConstant.V1_API_VERSION + "/mixture")
public class MixtureController {
    private final IMixtureService mixtureService;

    @GetMapping("/findNumberToCreate")
    public ResponseEntity<Long> findNumber(){
        return ResponseEntity.ok(mixtureService.findNumber()+1);
    }

}
