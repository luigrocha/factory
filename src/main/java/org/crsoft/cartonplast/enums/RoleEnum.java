package org.crsoft.cartonplast.enums;

import lombok.Getter;

/**
 * Role Enum
 *
 * @author jyepez on 5/5/2022
 */
@Getter
public enum RoleEnum {

    ADMIN("realm-admin"),
    SUPERVISOR("realm-supervisor"),
    USER("realm-user");

    private final String role;

    RoleEnum(String role) {
        this.role = role;
    }
}
