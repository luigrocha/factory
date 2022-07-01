package org.crsoft.cartonplast.celler.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 29/6/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypeMaterialStockVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Double stock;
}
