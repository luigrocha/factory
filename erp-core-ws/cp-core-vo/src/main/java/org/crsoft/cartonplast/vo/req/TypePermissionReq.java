package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 20/5/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TypePermissionReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Boolean flag;
}
