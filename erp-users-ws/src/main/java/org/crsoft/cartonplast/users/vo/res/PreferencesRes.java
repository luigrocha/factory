package org.crsoft.cartonplast.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * Preferences Response
 *
 * @author jyepez on 3/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PreferencesRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer code;
    private String colorMode;
    private String menuMode;
    private String menuTheme;
    private String topBarMode;
    private String color;
}
