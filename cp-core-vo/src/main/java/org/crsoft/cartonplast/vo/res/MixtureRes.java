package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Collection;

/**
 * @author jyepez on 7/7/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MixtureRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer prepare;
    private Integer preMixture;
    private String observation;
    private Integer number;
    private String documentBy;
    private String documentTo;
    private String mixture;
    private Double total;
    private Double totalReal;
    private DieRes die;
    private Timestamp date;
    private OrderRes order;
    private Collection<MixtureDetailRes> rows;

}
