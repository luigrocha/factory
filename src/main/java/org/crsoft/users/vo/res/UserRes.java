package org.crsoft.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * @author jyepez on 22/4/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private List<String> roles;
}
