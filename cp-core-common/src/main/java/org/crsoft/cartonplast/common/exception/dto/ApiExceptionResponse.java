package org.crsoft.cartonplast.common.exception.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lpillaga on 02/07/2022
 */
@AllArgsConstructor
@Data
@NoArgsConstructor
@Builder
public class ApiExceptionResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;

    private String message;

    private Integer status;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy hh:mm:ss")
    private LocalDateTime timestamp;

    private List<ApiInvalidParameterResponse> invalidParameters;
}
