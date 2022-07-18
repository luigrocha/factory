package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 17/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateMaterialRequestReceiptItemReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String product;
    private Double balance;
    private Double coat;
    private Double pallets;
    private Double weight;
    private Integer coatNumber;
    private Integer palletNumber;
    private String observation;
}
