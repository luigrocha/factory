package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;

import java.io.Serializable;

/**
 * @author lpillaga on 12/06/2022
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DieProductRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private String name;
    private Double area;
    private Double length;
    private Double width;
    private Integer gsmdis;
    private CatalogStatusRes status;
}
