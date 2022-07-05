package org.crsoft.cartonplast.common.exception;

import lombok.Getter;
import lombok.Setter;
import org.crsoft.cartonplast.common.exception.policy.ApplicationExceptionPolicy;

import static java.lang.String.format;

/**
 * @author lpillaga on 02/07/2022
 */
@Getter
@Setter
public class ApplicationException extends RuntimeException implements ApplicationExceptionPolicy {

    private final String code;
    private final String message;

    /**
     * Constructor accepting an exception reason.
     *
     * @param reason the reason of the exception
     */
    public ApplicationException(final ApplicationExceptionReason reason) {
        this.code = reason.getCode();
        this.message = reason.getMessage();
    }


    /**
     * Constructor accepting an excepting reason and optional parameters which are replaced in the
     * message.
     *
     * @param reason     the reason of the exception
     * @param parameters the optional parameters
     */
    public ApplicationException(final ApplicationExceptionReason reason, final Object... parameters) {
        if (parameters != null) {
            this.message = format(reason.getMessage(), parameters);
        } else {
            this.message = reason.getMessage();
        }

        this.code = reason.getCode();
    }


    @Override
    public String getLocalizedMessage() {
        return getMessage();
    }

    @Override
    public String toString() {
        return format("ApplicationException(code=%s, message=%s)", this.getCode(), this.getMessage());
    }
}