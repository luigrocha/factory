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

    private Integer order;
    private Integer number;
    private String documentTo;
    private String documentBy;
    private Timestamp date;
    private Integer prepare;
    private Double total;
    private String observation;
    private Collection<MixtureDetailReq> rows;
}
