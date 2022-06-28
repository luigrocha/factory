package org.crsoft.cartonplast.users.exception.policy;

import org.springframework.http.HttpStatus;

/**
 * @author lpillaga on 26/06/2022
 */
public interface BusinessExceptionPolicy extends ExceptionPolicy {
    HttpStatus getHttpStatus();
}
