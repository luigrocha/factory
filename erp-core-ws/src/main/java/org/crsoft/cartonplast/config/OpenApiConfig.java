package org.crsoft.cartonplast.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author lpillaga on 06/05/2022
 */
@Configuration
public class OpenApiConfig {

    @Value("${app.version}")
    String apiVersion;
    @Value("${app.title}")
    String apiTitle;
    @Value("${app.description}")
    String apiDescription;
    @Value("${app.apiTermsOfServiceUrl}")
    String apiTermsOfServiceUrl;
    @Value("${app.apiLicense}")
    String apiLicense;
    @Value("${app.apiLicenseUrl}")
    String apiLicenseUrl;

    @Bean
    public OpenAPI springShopOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title(apiTitle)
                        .description(apiDescription)
                        .version(apiVersion)
                        .termsOfService(apiTermsOfServiceUrl)
                        .license(new License().name(apiLicense).url(apiLicenseUrl)));
    }
}