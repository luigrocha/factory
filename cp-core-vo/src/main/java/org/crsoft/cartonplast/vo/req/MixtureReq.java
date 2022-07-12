package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author jyepez on 5/7/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MixtureReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer die;
    private Integer prepare;
    private Integer preMixture;
    private String observation;
    private Integer order;
    private Integer number;
    private String documentBy;
    private String documentTo;
    private String mixture;
    private Timestamp date;
    private Double total;
    private Double totalReal;
    private Collection<MixtureDetailReq> rows;
}
