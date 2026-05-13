package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 20/05/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UploadFileRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String fileName;
    private String fileType;
    private Long fileSize;
    private String fileUrl;
}
