package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author jyepez on 31/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellerRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String numberDocument;
    private String lote;
    private Integer amount;
    private Integer balance;
    private Integer coat;
    private Integer pallets;
    private Integer weight;
    private LocalDateTime date;
    private String observation;
    private MaterialRes material;
    private LocationRes location;
    private DocumentRes document;
}
