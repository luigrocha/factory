package org.crsoft.cartonplast.config.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 19/05/2022
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FileRes implements Serializable {

    private static final long serialVersionUID = 232836038145089522L;

    private String fileName;
    private String fileType;
    private Long fileSize;
    private String fileUrl;
}
