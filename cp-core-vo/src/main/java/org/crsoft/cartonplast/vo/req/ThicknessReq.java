package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 08/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ThicknessReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer weight;
    private Double thick;
}
