package org.crsoft.users.enums;

import lombok.Getter;

/**
 * Status Keycloak Enum
 *
 * @author jyepez
 */
@Getter
public enum StatusKeycloakEnum {

    OK(201),
    EXIST(409);

    private final Integer code;

    /**
     * Contructor.
     *
     * @param code
     */
    StatusKeycloakEnum(Integer code) {
        this.code = code;
    }
}
