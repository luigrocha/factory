package org.crsoft.cartonplast.users.exception;

/**
 * @author lpillaga on 30/05/2022
 */

public class ApiRequestException extends RuntimeException {

    public ApiRequestException(String message) {
        super(message);
    }

    public ApiRequestException(String message, Throwable cause) {
        super(message, cause);
    }
}
