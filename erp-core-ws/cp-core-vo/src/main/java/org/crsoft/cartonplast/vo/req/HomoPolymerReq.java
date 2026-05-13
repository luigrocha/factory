package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lpillaga on 11/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HomoPolymerReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer percentage;

    @NotEmpty
    private String hpCode;
}
