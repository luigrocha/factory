package org.crsoft.cartonplast.common.exception.policy;

import org.springframework.http.HttpStatus;

/**
 * @author lpillaga on 02/07/2022
 */
public interface BusinessExceptionPolicy extends ExceptionPolicy {
    HttpStatus getHttpStatus();
}
