package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author jyepez on 5/7/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MixtureDetailReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer mixture;
    private Integer material;
    private Double percent;
    private Double stop;
    private Double total;
}
