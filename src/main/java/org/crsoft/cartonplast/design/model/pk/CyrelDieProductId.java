package org.crsoft.cartonplast.design.model.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author lpillaga on 12/06/2022
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CyrelDieProductId implements Serializable {

    private Integer dieProductId;
    private Integer cyrelId;
}
