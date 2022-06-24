package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.crsoft.cartonplast.vo.res.DocumentRes;
import org.crsoft.cartonplast.vo.res.LocationRes;
import org.crsoft.cartonplast.vo.res.MaterialRes;

import java.io.Serializable;

/**
 * @author jyepez on 31/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellerDetailReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer material;
    private String lote;
    private Integer amount;
    private Double balance;
    private Double coat;
    private Double pallets;
    private Double weight;
    private Integer location;

}
