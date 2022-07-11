package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateColorBIdReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotBlank
    private String colorAId;
}
