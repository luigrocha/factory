package org.crsoft.cartonplast.users.exception;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

/**
 * @author lpillaga on 30/05/2022
 */
@AllArgsConstructor
@Data
public class ApiException {

    private String message;
    private HttpStatus httpStatus;
    private ZonedDateTime timestamp;
}
