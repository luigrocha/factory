package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 15/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequestDetailRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Double balance;
    private Double coat;
    private Double pallets;
    private Double weight;
    private Double consolidated;
    private Integer coatNumber;
    private Integer palletNumber;
    private String observation;
    private MaterialRes material;
}
