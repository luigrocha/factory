package org.crsoft.cartonplast.users.enums;

import lombok.Getter;

/**
 * Status Keycloak Enum
 *
 * @author jyepez
 */
@Getter
public enum KeycloakResponseStatus {

    OK(201),
    EXIST(409);

    private final Integer code;

    KeycloakResponseStatus(Integer code) {
        this.code = code;
    }
}
