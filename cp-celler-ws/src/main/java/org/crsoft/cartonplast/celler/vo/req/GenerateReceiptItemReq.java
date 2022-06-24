package org.crsoft.cartonplast.celler.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 04/06/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GenerateReceiptItemReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String productType;
    private String productName;
    private String lot;
    private Integer units;
    private Double bags1KG;
    private Double bags25KG;
    private Double pallets55;
    private Double totalWeight;
    private String location;
}
