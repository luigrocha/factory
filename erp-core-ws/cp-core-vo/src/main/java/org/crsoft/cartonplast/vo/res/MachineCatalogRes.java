package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 13/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineCatalogRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
}
