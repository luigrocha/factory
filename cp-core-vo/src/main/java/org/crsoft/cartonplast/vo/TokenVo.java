package org.crsoft.cartonplast.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 19/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TokenVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String access_token;
}
