package org.crsoft.cartonplast.vo.res;

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
public class CatalogStatusRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
    private String color;
    private String backgroundColor;
    private String type;
    private Boolean isVisible;
    private Boolean isDefault;
}
