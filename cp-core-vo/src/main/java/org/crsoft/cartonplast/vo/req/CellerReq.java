package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CellerReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String numberDocument;
    private LocalDateTime date;
    private LocalDateTime dateDocument;
    private Integer reason;
    private String observation;
    private String observations;
    private String origin;
    private String destiny;
    private String deliveredBy;
    private String receivedBy;
    private Collection<CellerDetailReq> cellerItems;

}
