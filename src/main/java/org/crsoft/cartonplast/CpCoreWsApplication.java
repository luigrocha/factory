package org.crsoft.cartonplast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import javax.annotation.PostConstruct;
import java.util.TimeZone;

@EntityScan(basePackages = {"org.crsoft.cartonplast"})
@SpringBootApplication(scanBasePackages = {"org.crsoft.cartonplast.*"})
@EnableJpaRepositories(basePackages = {"org.crsoft.cartonplast"})
@EnableFeignClients(basePackages = {"org.crsoft.cartonplast"})
@EnableJpaAuditing
public class CpCoreWsApplication {

    @PostConstruct
    public void init(){
        // Setting Spring Boot SetTimeZone
        TimeZone.setDefault(TimeZone.getTimeZone("America/Guayaquil"));
    }

    public static void main(String[] args) {
        SpringApplication.run(CpCoreWsApplication.class, args);
    }

}
