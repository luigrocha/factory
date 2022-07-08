package org.crsoft.cartonplast.common.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.crsoft.cartonplast.common.exception.policy.BusinessExceptionPolicy;
import org.springframework.http.HttpStatus;

/**
 * @author lpillaga on 02/07/2022
 */
@Getter
@AllArgsConstructor
public enum BusinessExceptionReason implements BusinessExceptionPolicy {

    THICKNESS_NOT_FOUND("thickness_not_found", "No se encontró el grosor para el id %s", HttpStatus.NOT_FOUND),
    CLIENT_NOT_FOUND("client_not_found", "No se encontró el cliente con el id %s", HttpStatus.NOT_FOUND),
    CATALOG_PRIORITY_NOT_FOUND("catalog_priority_not_found", "No se encontró la prioridad con el id %s", HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND("project_not_found", "No se encontró el proyecto con el id %s", HttpStatus.NOT_FOUND),
    DEFAULT_ORDER_STATUS_NOT_FOUND("default_order_status_not_found", "No se encontró el estado de orden por defecto", HttpStatus.NOT_FOUND),
    REPEATED_ORDER_CODE("repeated_order_code", "El código de orden '%s' ya existe", HttpStatus.BAD_REQUEST);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}