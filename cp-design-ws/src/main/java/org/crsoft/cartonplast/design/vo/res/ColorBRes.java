package org.crsoft.cartonplast.design.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 11/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorBRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private Integer index;
    private Double dosage;
    private String description;
    private String colorCode;
    private ColorARes colorA;
    private String observation;
}
