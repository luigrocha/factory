package org.crsoft.cartonplast.config.exception;

import org.crsoft.cartonplast.config.constant.GlobalErrorCode;

/**
 * @author lpillaga on 19/05/2022
 */
public class UploadFailedException extends ConfigWsGlobalException {

    public UploadFailedException() {
        super("Error al subir el archivo", GlobalErrorCode.ERROR_UPLOAD_FILE);
    }

    public UploadFailedException(String message) {
        super(message, GlobalErrorCode.ERROR_UPLOAD_FILE);
    }
}
