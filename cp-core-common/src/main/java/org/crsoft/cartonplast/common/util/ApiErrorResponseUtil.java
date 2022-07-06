package org.crsoft.cartonplast.common.util;

import org.crsoft.cartonplast.common.exception.dto.ApiExceptionResponse;
import org.crsoft.cartonplast.common.exception.dto.ApiInvalidParameterResponse;
import org.springframework.http.HttpStatus;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

/**
 * @author lpillaga on 02/07/2022
 */
public class ApiErrorResponseUtil {

    private ApiErrorResponseUtil() {
    }

    public static ApiExceptionResponse build(
            final String code,
            final String message,
            final HttpStatus status) {
        return buildDetails(code, message, status);
    }

    public static ApiExceptionResponse build(
            final String code,
            final String message,
            final HttpStatus status,
            final List<ApiInvalidParameterResponse> invalidParameters) {
        return buildDetails(code, message, status, invalidParameters);
    }

    private static ApiExceptionResponse buildDetails(
            final String code,
            final String message,
            final HttpStatus status) {
        return buildDetails(code, message, status, null);
    }

    private static ApiExceptionResponse buildDetails(
            final String code,
            final String message,
            final HttpStatus status,
            final List<ApiInvalidParameterResponse> invalidParameters) {
        final ApiExceptionResponse errorResponseDetails = new ApiExceptionResponse();
        errorResponseDetails.setCode(code);
        errorResponseDetails.setMessage(message);
        if (!Objects.isNull(status)) {
            errorResponseDetails.setStatus(status.value());
        }
        errorResponseDetails.setTimestamp(LocalDateTime.now());
        if (!CollectionUtils.isEmpty(invalidParameters)) {
            errorResponseDetails.setInvalidParameters(invalidParameters);
        }
        return errorResponseDetails;
    }
}
