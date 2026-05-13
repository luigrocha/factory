package org.crsoft.cartonplast.config.vo.res;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author lpillaga on 19/05/2022
 */
@Getter
@Setter
@Builder
public class ErrorRes {

    private String code;
    private String message;
}
