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

    COLOR_A_NOT_FOUND("color_a_not_found", "No se encontró el color A con código %s", HttpStatus.NOT_FOUND),
    COLOR_B_NOT_FOUND("color_b_not_found", "No se encontró el color B con código %s", HttpStatus.NOT_FOUND),
    COLOR_A_ALREADY_EXISTS("color_a_already_exists", "El color A con código %s ya existe", HttpStatus.CONFLICT),
    COLOR_B_ALREADY_EXISTS("color_b_already_exists", "El color B con código %s ya existe", HttpStatus.CONFLICT),
    HOMOPOLYMER_ALREADY_EXISTS("homopolymer_already_exists", "El homopolimero con código %s ya existe", HttpStatus.CONFLICT),
    HOMOPOLYMER_NOT_FOUND("homopolymer_not_found", "No se encontró el homopolimero con código %s", HttpStatus.NOT_FOUND),
    PRINTER_NOT_FOUND("printer_not_found", "No se encontró la impresora con código %s", HttpStatus.NOT_FOUND),
    COLOR_CATALOG_NOT_FOUND("color_catalog_not_found", "No se encontró el catálogo de colores con código %s", HttpStatus.NOT_FOUND),
    LOCATION_NOT_FOUND("location_not_found", "No se encontró la ubicación con código %s", HttpStatus.NOT_FOUND),
    THICKNESS_NOT_FOUND("thickness_not_found", "No se encontró el grosor para el id %s", HttpStatus.NOT_FOUND),
    CLIENT_NOT_FOUND("client_not_found", "No se encontró el cliente con el id %s", HttpStatus.NOT_FOUND),
    CATALOG_PRIORITY_NOT_FOUND("catalog_priority_not_found", "No se encontró la prioridad con el id %s", HttpStatus.NOT_FOUND),
    PROJECT_NOT_FOUND("project_not_found", "No se encontró el proyecto con el id %s", HttpStatus.NOT_FOUND),
    DEFAULT_ORDER_STATUS_NOT_FOUND("default_order_status_not_found", "No se encontró el estado de orden por defecto", HttpStatus.NOT_FOUND),
    ORDER_NOT_FOUND("order_not_found", "No se encontró la orden con el id %s", HttpStatus.NOT_FOUND),
    CATALOG_STATUS_NOT_FOUND("catalog_status_not_found", "No se encontró el estado del catálogo con el id %s", HttpStatus.NOT_FOUND),
    REPEATED_ORDER_CODE("repeated_order_code", "El código de orden '%s' ya existe", HttpStatus.BAD_REQUEST),
    MIXTURE_NOT_FOUND("mixture_not_found", "No se encontró la orden de mezcla para el id %s", HttpStatus.NOT_FOUND),
    TURN_NOT_FOUND("turn_not_found", "No se encontró el turno con el id %s", HttpStatus.NOT_FOUND),
    MATERIAL_REQUEST_NOT_FOUND("material_request_not_found", "No se encontró la solicitud de material con el id %s", HttpStatus.NOT_FOUND),
    MATERIAL_REQUEST_RECEIPT_FAILED("material_request_receipt_failed", "No se pudo generar el comprobante de solicitud de material para el id %s", HttpStatus.INTERNAL_SERVER_ERROR),
    MIXTURE_RECEIPT_FAILED("mixture_receipt_failed", "No se pudo generar el comprobante de mezcla", HttpStatus.INTERNAL_SERVER_ERROR);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}