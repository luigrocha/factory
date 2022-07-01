package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;


/**
 * @author jyepez on 14/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String productCode;
    private String name;
    private Integer quantity;
    private LocalDateTime orderedAt;
    private String lot;
    private LocalDateTime estimatedDeliveryAt;
    private String clientOrderCode;
    private String observation;
    private LocalDateTime completedAt;
    private LocalDateTime canceledAt;
    private LocalDateTime productionStartedAt;
    private String cancellationReason;
    private LocalDateTime lastModifiedAt;
    private ClientShortRes client;
    private CatalogStatusRes status;
    private Integer pendingQuantity;
    private Integer shippedQuantity;
    private CatalogPriorityRes priority;
}
