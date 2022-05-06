package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 26/4/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class TestRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String attribute;
}
