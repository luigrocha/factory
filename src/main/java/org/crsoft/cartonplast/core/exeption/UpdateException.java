package org.crsoft.cartonplast.core.exeption;

import lombok.Getter;

/**
 * Update Exception
 *
 * @author jyepez on 25/4/2022
 */
@Getter
public class UpdateException extends Exception {

    private final String objetName;

    public UpdateException(String objetName, String message) {
        super(message);
        this.objetName = objetName;
    }

    public UpdateException(String objetName, String message, Throwable cause) {
        super(message, cause);
        this.objetName = objetName;
    }
}
