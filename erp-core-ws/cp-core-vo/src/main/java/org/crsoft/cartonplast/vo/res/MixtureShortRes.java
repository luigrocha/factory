package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Collection;

/**
 * @author jyepez on 7/7/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MixtureShortRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer number;
    private String mixture;
    private OrderRes order;
    
}
