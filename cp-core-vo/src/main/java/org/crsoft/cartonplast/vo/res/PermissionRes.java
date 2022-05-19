package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 19/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String role;
    private Integer menu;
    private Boolean create;
    private Boolean read;
    private Boolean update;
    private Boolean delete;
}
