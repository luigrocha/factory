package org.crsoft.cartonplast.design.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.design.vo.res.MachineRes;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lpillaga on 13/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DieReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String name;

    private String dsbMultiple;

    private String observations;

    @NotNull
    private Double quantity;

    @NotNull
    private Double quantityLength;

    @NotNull
    private Double separationLength;

    @NotNull
    private Double quantityWidth;

    @NotNull
    private Double separationWidth;

    @NotNull
    private Double borderLength;

    @NotNull
    private Double borderWidth;

    @NotNull
    private Double leafLength;

    @NotNull
    private Double leafWidth;

    private Integer manufacturerId;

    @NotNull
    private Integer dieProductId;

    private List<Integer> machines;
}
