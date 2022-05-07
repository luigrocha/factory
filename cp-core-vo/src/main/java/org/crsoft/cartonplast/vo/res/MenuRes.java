package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author jyepez on 6/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String label;
    private String icon;
    private Boolean condition;
    private Collection<String> routerLink;
    private Collection<MenuRes> items;
    private Integer order;
}
