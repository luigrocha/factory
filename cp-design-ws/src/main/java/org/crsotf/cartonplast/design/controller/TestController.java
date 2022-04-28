package org.crsotf.cartonplast.design.controller;

import org.crsoft.cartonplast.vo.res.TestRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jyepez on 26/4/2022
 */
@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    @GetMapping("/findTest")
    public ResponseEntity<TestRes> findTest() {
        return ResponseEntity.ok().body(TestRes.builder().attribute("design...").build());
    }
}
