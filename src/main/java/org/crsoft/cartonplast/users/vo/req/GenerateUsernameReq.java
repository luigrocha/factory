package org.crsoft.cartonplast.users.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author lpillaga on 25/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateUsernameReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;
}
