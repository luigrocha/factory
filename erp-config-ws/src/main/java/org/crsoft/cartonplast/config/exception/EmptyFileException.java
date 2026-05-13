package org.crsoft.cartonplast.config.exception;


import org.crsoft.cartonplast.config.constant.GlobalErrorCode;

/**
 * @author lpillaga on 19/05/2022
 */

public class EmptyFileException extends ConfigWsGlobalException {
    public EmptyFileException() {
        super("No se ha cargado ningun archivo", GlobalErrorCode.ERROR_EMPTY_FILE);
    }

    public EmptyFileException(String message) {
        super(message, GlobalErrorCode.ERROR_EMPTY_FILE);
    }
}
