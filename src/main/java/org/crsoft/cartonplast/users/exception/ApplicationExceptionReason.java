package org.crsoft.cartonplast.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.crsoft.cartonplast.users.exception.policy.ApplicationExceptionPolicy;

/**
 * @author lpillaga on 26/06/2022
 */
@Getter
@AllArgsConstructor
public enum ApplicationExceptionReason implements ApplicationExceptionPolicy {

    BEAN_PROPERTY_NOT_EXISTS("bean_property_not_exists", "Property '%s' for object '%s' doesn't exists");

    private final String code;
    private final String message;
}
