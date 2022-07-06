package org.crsoft.cartonplast.vo.res;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

/**
 * @author lpillaga on 02/07/2022
 */
@Data
@Builder
public class GeneratedOrderCodeRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String lastOrderCode;
    private String nextOrderCode;
}
