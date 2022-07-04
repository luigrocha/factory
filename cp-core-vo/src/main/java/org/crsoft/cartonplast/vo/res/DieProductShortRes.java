package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 12/06/2022
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DieProductShortRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private String name;
    private Double length;
    private Double width;
}
