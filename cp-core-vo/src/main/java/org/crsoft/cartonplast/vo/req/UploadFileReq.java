package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import java.io.Serializable;
import java.util.List;

/**
 * @author lpillaga on 20/05/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String bucketName;
    private List<String> directory;
    @SuppressWarnings("java:S1948")
    private MultipartFile file;
}
