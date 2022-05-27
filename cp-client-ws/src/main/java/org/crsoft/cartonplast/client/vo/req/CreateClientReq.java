package org.crsoft.cartonplast.client.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author lpillaga on 20/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreateClientReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull
    private String id;

    @NotNull
    private String name;

    private Integer categoryId;

    @SuppressWarnings("java:S1948")
    private MultipartFile logo;
}
