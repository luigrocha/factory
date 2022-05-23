package org.crsoft.cartonplast.common.constant;

import java.util.List;

/**
 * @author lpillaga on 09/05/2022
 */
public class GlobalConstant {

    private GlobalConstant() {
    }

    public static final String LOCALHOST_URI = "http://localhost:4200";
    public static final String WEBAPP_TEST_URI = "https://webapp-test.carton-plast.com";
    public static final String CROSS_PRODUCTION = "https://webapp.carton-plast.com";
    public static final String V1_API_VERSION = "/api/v1";
    public static final List<String> CLIENTS_IMAGES_DIRECTORY = List.of("clients");
}

