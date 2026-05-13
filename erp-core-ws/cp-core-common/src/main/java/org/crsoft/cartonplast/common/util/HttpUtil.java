package org.crsoft.cartonplast.common.util;

import org.springframework.http.HttpHeaders;

/**
 * @author lpillaga on 04/06/2022
 */
public class HttpUtil {

    private HttpUtil() {
    }

    public static HttpHeaders getDefaultPDFHeaders(String fileName) {
        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/pdf");
        headers.set("Content-Disposition", "inline; filename=" + fileName + ".pdf");
        return headers;
    }
}
