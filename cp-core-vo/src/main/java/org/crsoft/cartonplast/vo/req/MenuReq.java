package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 6/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MenuReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String label;
    private String data;
    private String icon;
    private String url;
    private String role;
    private Integer child;
}
