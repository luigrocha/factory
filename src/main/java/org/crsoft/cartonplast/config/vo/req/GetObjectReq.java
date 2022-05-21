package org.crsoft.cartonplast.config.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lpillaga on 20/05/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetObjectReq implements Serializable {

    private static final long serialVersionUID = 232836038145089522L;

    @NotEmpty
    private String bucketName;

    @NotNull
    private String objectName;
}
