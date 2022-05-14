package org.crsoft.cartonplast.app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@EntityScan(basePackages = {"org.crsoft.cartonplast"})
@SpringBootApplication(scanBasePackages = {"org.crsoft.cartonplast", "org.crsoft.cartonplast.design"})
@EnableJpaRepositories(basePackages = {"org.crsoft.cartonplast"})
public class CpCoreWsApplication {

    public static void main(String[] args) {
        SpringApplication.run(CpCoreWsApplication.class, args);
    }

}
