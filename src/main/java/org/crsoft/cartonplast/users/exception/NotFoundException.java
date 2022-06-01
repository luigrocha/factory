package org.crsoft.cartonplast.users.exception;

/**
 * Not Found Exception
 *
 * @author jyepez on 25/4/2022
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
