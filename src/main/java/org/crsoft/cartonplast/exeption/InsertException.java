package org.crsoft.cartonplast.exeption;

import lombok.Getter;

/**
 * Insert Exception
 *
 * @author jyepez on 25/4/2022
 */
@Getter
public class InsertException extends Exception {

    private final String objetName;

    public InsertException(String objetName, String message) {
        super(message);
        this.objetName = objetName;
    }

    public InsertException(String objetName, String message, Throwable cause) {
        super(message, cause);
        this.objetName = objetName;
    }
}
