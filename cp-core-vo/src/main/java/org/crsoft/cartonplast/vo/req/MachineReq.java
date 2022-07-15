package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MachineReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String name;

    private Double widthExt;

    private Boolean hasDesb;

    private Integer abilityPell;

    private String observation;

    private String description;

    @NotEmpty
    private Integer machineCatalog;
}
