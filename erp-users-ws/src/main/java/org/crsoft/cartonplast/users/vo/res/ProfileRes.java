package org.crsoft.cartonplast.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProfileRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
    private PersonRes person;
    private Collection<String> roles;
}
