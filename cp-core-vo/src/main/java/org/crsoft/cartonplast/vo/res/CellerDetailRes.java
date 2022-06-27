package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jyepez on 31/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellerDetailRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String lote;
    private Integer amount;
    private Double balance;
    private Double coat;
    private Double pallets;
    private Double weight;
    // private CellerRes celler;
    private MaterialRes material;
    private LocationRes location;
    private DocumentRes document;
}
