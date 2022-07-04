package org.crsoft.cartonplast.common.exception.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 02/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApiInvalidParameterResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String parameter;
    private String message;
}
