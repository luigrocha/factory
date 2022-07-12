package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 08/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateMixtureReceiptItemReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String type;
    private String product;
    private String lot;
    private Double percentage;
    private Double stopQuantity;
    private Double totalQuantity;
}
