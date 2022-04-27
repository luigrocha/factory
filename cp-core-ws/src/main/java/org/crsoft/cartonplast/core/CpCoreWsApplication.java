package org.crsoft.cartonplast.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(scanBasePackages={"org.crsfotf.cartonplast.desing"})
public class CpCoreWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpCoreWsApplication.class, args);
    }

}
