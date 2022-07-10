package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lpillaga on 08/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateMixtureReceiptReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private String mixtureCode;
    private String number;
    private String lot;
    private String clientProd;
    private String isToExport;
    private String dieProduct;
    private String die;
    private String cyrel;
    private Integer length;
    private Integer width;
    private String documentBy;
    private LocalDateTime date;
    private Integer prepare;
    private Integer totalKg;
    private Integer leafs;
    private Integer preMixtureKg;
    private String mixture;
    private Integer weight;

    private BigDecimal totalPercentage;
    private BigDecimal totalStopQuantity;
    private BigDecimal total;

    private List<GenerateMixtureReceiptItemReq> items;
}
