package org.crsoft.cartonplast.design.model.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author lpillaga on 05/05/2022
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class DieMachineId implements Serializable {

    private Integer machineId;
    private Integer dieId;
}
