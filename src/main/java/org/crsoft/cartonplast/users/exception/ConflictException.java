package org.crsoft.cartonplast.users.exception;

/**
 * @author lpillaga on 30/05/2022
 */
public class ConflictException extends RuntimeException {
    public ConflictException(String message) {
        super(message);
    }
}