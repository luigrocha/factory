package org.crsoft.cartonplast.users.model.pk;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * @author lpillaga on 31/05/2022
 */
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonJobPositionId implements Serializable {

    private Integer personId;
    private Integer jobPositionId;
}
