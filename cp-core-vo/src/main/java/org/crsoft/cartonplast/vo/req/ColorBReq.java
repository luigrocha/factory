package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author lpillaga on 10/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorBReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String id;

    private Integer index;
    private Double dosage;

    @NotEmpty
    private String description;
    private String colorCode;
    private String observation;

    @NotEmpty
    private String colorAId;
}
