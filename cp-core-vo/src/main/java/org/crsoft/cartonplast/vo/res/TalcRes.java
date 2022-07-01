package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 23/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TalcRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer percentage;
    private String lpCode;
}
