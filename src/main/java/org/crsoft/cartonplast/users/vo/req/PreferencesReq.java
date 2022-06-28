package org.crsoft.cartonplast.users.vo.req;

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
public class PreferencesReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String colorMode;
    private String menuMode;
    private String menuTheme;
    private String topBarMode;
    private String color;
}
