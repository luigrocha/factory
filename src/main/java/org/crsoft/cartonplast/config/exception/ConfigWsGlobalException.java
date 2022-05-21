package org.crsoft.cartonplast.config.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * @author lpillaga on 19/05/2022
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ConfigWsGlobalException extends RuntimeException {

    private String code;
    private String message;

    public ConfigWsGlobalException(String message) {
        super(message);
    }
}
