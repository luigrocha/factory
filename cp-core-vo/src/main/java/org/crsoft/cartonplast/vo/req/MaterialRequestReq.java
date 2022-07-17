package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jyepez on 16/7/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequestReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String number;

    @NotEmpty
    private LocalDateTime date;

    @NotEmpty
    private String documentByUsername;

    @NotEmpty
    private String documentBy;

    @NotEmpty
    private String status;

    @NotEmpty
    private Integer turn;

}
