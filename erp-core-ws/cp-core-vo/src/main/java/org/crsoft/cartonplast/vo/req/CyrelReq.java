package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lpillaga on 16/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CyrelReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String print;

    @NotEmpty
    private String description;

    private String description2;

    private String observation;

    @NotNull
    private Integer printerId;

    private String mbLeafId;

    private String catalogStatusId;

    @NotNull
    @Min(1)
    private List<Integer> dieProductIds;

    private List<CyrelColorReq> cyrelColors;
}
