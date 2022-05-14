package org.crsoft.cartonplast.design.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 14/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorCatalogRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
}
