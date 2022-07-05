package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDate;

/**
 * @author lpillaga on 30/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateOrderReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String code;

    @NotEmpty
    private String productCode;

    @NotEmpty
    private String name;

    @NotNull
    private Integer quantity;

    @NotEmpty
    private String clientOrderCode;

    @NotEmpty
    private String observation;

    @NotNull
    private LocalDate estimatedDeliveryAt;

    @NotNull
    private Integer clientId;

    @NotNull
    private String priorityId;

    @NotNull
    private Integer projectId;
}
