package org.crsoft.cartonplast.common.exception;

/**
 * @author lpillaga on 21/05/2022
 */
public class ConflictException extends RuntimeException {

    private static final long serialVersionUID = -81207908739079087L;

    public ConflictException(String message) {
        super(message);
    }
}
