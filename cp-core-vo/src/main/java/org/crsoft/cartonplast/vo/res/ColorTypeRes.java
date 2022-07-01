package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 14/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ColorTypeRes implements Serializable {

    private static final long serialVersionUID = -8792709844444444444L;

    private String id;
    private String name;
}
