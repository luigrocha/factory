package org.crsoft.cartonplast.design.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 12/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DieProductReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String code;
    private String name;
    private Double area;
    private Double length;
    private Double width;
    private Double gsmdis;
    private String statusId;
}
