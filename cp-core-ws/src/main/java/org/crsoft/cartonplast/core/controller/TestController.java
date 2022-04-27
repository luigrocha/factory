package org.crsoft.cartonplast.core.controller;

import org.crsfotf.cartonplast.desing.service.impl.TestService;
import org.crsoft.cartonplast.vo.res.TestRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jyepez on 27/4/2022
 */
@RestController
@RequestMapping("/test")
@CrossOrigin
public class TestController {

    private final TestService testService;

    public TestController(TestService testService) {
        this.testService = testService;
    }


    @GetMapping("/findTest")
    public ResponseEntity<TestRes> findTest() {
        return ResponseEntity.ok().body(this.testService.findTest());
    }
}