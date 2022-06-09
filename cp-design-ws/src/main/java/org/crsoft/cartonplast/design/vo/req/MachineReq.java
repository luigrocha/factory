package org.crsoft.cartonplast.design.vo.req;

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

    private Integer id;

    @NotEmpty
    private String name;

    @NotNull
    private Boolean hasDesb;
}
