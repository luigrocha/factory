package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author lpillaga on 12/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CancelOrderReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String cancellationReason;

    @NotEmpty
    private String statusCode;
}
