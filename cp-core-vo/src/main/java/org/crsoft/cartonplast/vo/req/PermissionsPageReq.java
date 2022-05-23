package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author jyepez on 21/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PermissionsPageReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String url;
    private Collection<String> roles;

}
