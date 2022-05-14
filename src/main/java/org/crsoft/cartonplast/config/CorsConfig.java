package org.crsoft.cartonplast.config;

import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author lpillaga on 03/05/2022
 */
@Configuration
public class CorsConfig {

    @Bean
    public WebMvcConfigurer corsConfigurer() {
        return new WebMvcConfigurer() {
            @Override
            public void addCorsMappings(CorsRegistry registry) {
                registry.addMapping("/**")
                        .allowedOrigins(
                                GlobalConstant.LOCALHOST_URI,
                                GlobalConstant.WEBAPP_TEST_URI,
                                GlobalConstant.CROSS_PRODUCTION
                        )
                        .exposedHeaders("Content-Disposition")
                        .allowedMethods("*");
            }
        };
    }
}