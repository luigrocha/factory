package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 19/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorCatalogReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;

    private String name;

    private String colorCode;
}
