package org.crsoft.cartonplast.common.constant;

import java.util.List;

/**
 * @author lpillaga on 09/05/2022
 */
public class GlobalConstant {

    public static final String LOCALHOST_URI = "http://localhost:4200";
    public static final String WEBAPP_DEV_URI = "https://webapp-dev.carton-plast.com";
    public static final String WEBAPP_TEST_URI = "https://webapp-test.carton-plast.com";
    public static final String CROSS_PRODUCTION = "https://webapp.carton-plast.com";
    public static final String V1_API_VERSION = "/api/v1";
    public static final List<String> CLIENTS_IMAGES_DIRECTORY = List.of("clients");
    public static final List<String> CYREL_DOCUMENTS_DIRECTORY = List.of("cireles");
    public static final List<String> DIE_DOCUMENTS_DIRECTORY = List.of("troqueles");
    public static final String URL_USER = "https://users-test.carton-plast.com";
    public static final String V1_API_VERSION_USER = "/api/v1";

    public static final String FILES_PATH = "files";

    public static final String LOGO_CP_PATH = "classpath:images/cp-logo.png";

    public static final String EC_CODE = "EC";

    public static final String ES_LANG_CODE = "es";

    private GlobalConstant() {
    }
}

