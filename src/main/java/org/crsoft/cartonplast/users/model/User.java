package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

/**
 * User
 *
 * @author jyepez
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String userName;
    private String email;
    private String firstName;
    private String lastName;
    private String password;
    private List<String> roles;
}
