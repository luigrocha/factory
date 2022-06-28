package org.crsoft.cartonplast.users.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.crsoft.cartonplast.users.exception.policy.BusinessExceptionPolicy;
import org.springframework.http.HttpStatus;

/**
 * @author lpillaga on 26/06/2022
 */
@Getter
@AllArgsConstructor
public enum BusinessExceptionReason implements BusinessExceptionPolicy {

    PERSON_NOT_FOUND("person_not_found", "La persona con el id '%s' no existe", HttpStatus.NOT_FOUND),
    USER_ALREADY_EXISTS_KEYCLOAK("user_already_exists_keycloak", "El usuario '%s' ya existe en Keycloak", HttpStatus.CONFLICT),
    CREATED_KEYCLOAK_USER_FAILED("created_keycloak_user_failed", "No se pudo crear el usuario '%s' en Keycloak", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_UPDATE_FAILED("user_update_failed", "No se pudo actualizar el usuario '%s' en Keycloak", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_KEYCLOAK_DELETE_FAILED("user_keycloak_delete_failed", "No se pudo eliminar el usuario en Keycloak", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_LOCAL_DELETE_FAILED("user_local_delete_failed", "No se pudo eliminar el usuario en el sistema", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_NOT_FOUND_KEYCLOAK("user_not_found_keycloak", "El usuario '%s' no existe en Keycloak", HttpStatus.NOT_FOUND),
    USER_LOCAL_NOT_FOUND("user_local_not_found", "El usuario local '%s' no existe", HttpStatus.NOT_FOUND),
    PREFERENCES_NOT_FOUND("preferences_not_found", "Las preferencias del usuario '%s' no existen", HttpStatus.NOT_FOUND),
    ROLE_NOT_FOUND("role_not_found", "El rol '%s' no existe", HttpStatus.NOT_FOUND),
    ROLE_USER_UPDATE_FAILED("role_user_update_failed", "No se pudo actualizar el rol del usuario en Keycloak", HttpStatus.INTERNAL_SERVER_ERROR),
    USER_PROFILE_NOT_FOUND("user_profile_not_found", "El perfil del usuario '%s' no existe", HttpStatus.NOT_FOUND);

    private final String code;
    private final String message;
    private final HttpStatus httpStatus;

}