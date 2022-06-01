package org.crsoft.cartonplast.users.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GroupReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String name;

    // TODO: Preguntar a Wilmer que es lo que se debe hacer con este campo
    private Boolean ch;

    @NotEmpty
    private String firstDigit;

    @NotEmpty
    private String secondDigit;

    @NotEmpty
    private String division;
}
