package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ManufacturerReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String description;
    private String observation;
}
