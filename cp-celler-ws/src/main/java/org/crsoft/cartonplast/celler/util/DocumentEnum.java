package org.crsoft.cartonplast.celler.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author jyepez on 26/6/2022
 */
@Getter
@AllArgsConstructor
public enum DocumentEnum {

    CIB("CIB", 1),
    CEB("CEB", 2),
    MOV("MOV", 3),
    TM1("TM1", 4),
    TM5("TM5", 5),
    CEP("CEP", 6);

    private final String name;
    private final Integer code;

}
