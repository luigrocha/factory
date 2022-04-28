package org.crsoft.cartonplast.core;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"org.crsoft.cartonplast.core","org.crsotf.cartonplast.design"})
public class CpCoreWsApplication {

	public static void main(String[] args) {
		SpringApplication.run(CpCoreWsApplication.class, args);
	}

}
