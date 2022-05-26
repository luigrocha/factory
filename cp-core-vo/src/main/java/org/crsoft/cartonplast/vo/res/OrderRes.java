package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.Date;

/**
 * @author jyepez on 14/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class OrderRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lote;
    private ClientRes client;
    private Integer amount;
    private Date deliverAt;
    private Integer order;
    private String observation;
    private Integer difference;
    private CatalogStatusRes status;
    private CatalogPriorityRes priority;
}
