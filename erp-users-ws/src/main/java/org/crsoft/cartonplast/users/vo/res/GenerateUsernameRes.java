package org.crsoft.cartonplast.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 25/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateUsernameRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String username;
}
