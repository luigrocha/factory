package org.crsoft.cartonplast.design.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lpillaga on 17/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CyrelColorReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private Integer index;

    private String observation;

    private Integer cyrelId;

    @NotNull
    private ColorCatalogReq color;

    @NotEmpty
    private String colorType;
}
