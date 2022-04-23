package org.crsoft.users.vo.req;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
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
    @JsonIgnore
    private String password;
}
