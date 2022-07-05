package org.crsoft.cartonplast.mixture.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.mixture.service.IMixtureService;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureMapper;
import org.crsoft.cartonplast.vo.req.MixtureReq;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

/**
 * @author jyepez on 3/7/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(GlobalConstant.V1_API_VERSION + "/mixture")
public class MixtureController {
    private final IMixtureService mixtureService;
    private final MixtureMapper mixtureMapper;

    @GetMapping("/findNumberToCreate")
    public ResponseEntity<Long> findNumber() {
        return ResponseEntity.ok(mixtureService.findNumber() + 1);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MixtureReq mixtureReq, @RequestHeader("userName") String userName)
            throws InsertException {
        Mixture mixture = this.mixtureMapper.mixtureResToMixture(mixtureReq);
        mixture.setCreatedBy(userName);
        this.mixtureService.create(mixture,mixtureReq.getRows());
        return ResponseEntity.ok().build();
    }
}
