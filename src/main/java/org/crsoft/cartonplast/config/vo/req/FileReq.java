package org.crsoft.cartonplast.config.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lpillaga on 19/05/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileReq implements Serializable {

    private static final long serialVersionUID = 232836038145089522L;

    @NotEmpty
    private String bucketName;

    private List<String> directory;

    @SuppressWarnings("java:S1948")
    @NotNull
    private MultipartFile file;
}
