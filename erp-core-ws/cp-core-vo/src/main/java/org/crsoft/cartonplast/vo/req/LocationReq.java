package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author lpillaga on 11/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LocationReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String location;
    private String description;
}
