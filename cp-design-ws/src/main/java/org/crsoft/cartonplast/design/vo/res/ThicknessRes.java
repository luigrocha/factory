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
public class ThicknessRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer weight;
    private Double thickness;
}
