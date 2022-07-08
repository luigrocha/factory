package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 7/7/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MixtureDetailRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Double percent;
    private Double stop;
    private Double total;
    private MaterialRes material;

}
