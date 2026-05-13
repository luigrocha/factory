package org.crsoft.cartonplast.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 30/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SecondDigitRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;
    private String name;
}
