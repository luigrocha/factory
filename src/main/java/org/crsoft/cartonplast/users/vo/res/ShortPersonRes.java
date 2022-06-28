package org.crsoft.cartonplast.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 26/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ShortPersonRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String ci;
    private String firstLastName;
    private String secondLastName;
    private String firstName;
    private String secondName;
    private String fullName;
    private String email1;
}
