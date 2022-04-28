package org.crsoft.cartonplast.users.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

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
public class UserReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
}
