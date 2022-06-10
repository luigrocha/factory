package org.crsoft.cartonplast.design.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 09/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private Boolean hasDesb;
}
