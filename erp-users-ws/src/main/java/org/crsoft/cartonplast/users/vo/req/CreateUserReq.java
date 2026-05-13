package org.crsoft.cartonplast.users.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * User Req
 *
 * @author jyepez on 22/4/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateUserReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String username;

    @NotEmpty
    @Email
    private String email;

    @NotEmpty
    private String firstName;

    @NotEmpty
    private String lastName;

    @NotEmpty
    private String password;

    @NotNull
    private Integer personId;
}
