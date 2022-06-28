package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author jyepez on 28/6/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CellerStockRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private MaterialRes material;
    private Collection<LocationStockRes> locationStock;
    private Double stock;

}
