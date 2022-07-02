package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 01/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectShortRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String codeGen;
}
