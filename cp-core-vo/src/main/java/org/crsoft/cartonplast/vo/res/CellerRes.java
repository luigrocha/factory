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
    private LocalDateTime date;
    private LocalDateTime dateDocument;
    private String reason;
    private String observation;
    private String observations;
    private String origin;
    private String destiny;
    private Boolean state;

}
