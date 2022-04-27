package org.crsfotf.cartonplast.desing.service.impl;

import lombok.extern.log4j.Log4j2;
import org.crsfotf.cartonplast.desing.service.ITestService;
import org.crsoft.cartonplast.vo.res.TestRes;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Service;

/**
 * @author jyepez on 26/4/2022
 */
@Service
@Log4j2
public class TestService implements ITestService {


    @Override
    public TestRes findTest() {
        log.info("Console Test");
        return TestRes.builder().attribute("Hello Word, I'm Test").build();
    }
}
