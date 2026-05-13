package org.crsoft.cartonplast.config.exception;

import org.crsoft.cartonplast.config.constant.GlobalErrorCode;

/**
 * @author lpillaga on 20/05/2022
 */
public class FileNotFoundException extends ConfigWsGlobalException {
    public FileNotFoundException() {
        super("No se pudo encontrar el archivo", GlobalErrorCode.ERROR_FILE_NOT_FOUND);
    }

    public FileNotFoundException(String message) {
        super(message, GlobalErrorCode.ERROR_FILE_NOT_FOUND);
    }
}
