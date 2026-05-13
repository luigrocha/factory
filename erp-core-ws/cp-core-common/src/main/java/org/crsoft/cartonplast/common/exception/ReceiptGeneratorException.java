package org.crsoft.cartonplast.common.exception;

/**
 * @author lpillaga on 04/06/2022
 */
public class ReceiptGeneratorException extends RuntimeException {

    private static final long serialVersionUID = -81207907908769098L;

    public ReceiptGeneratorException(String message) {
        super(message);
    }

    public ReceiptGeneratorException(String message, Throwable cause) {
        super(message, cause);
    }
}
