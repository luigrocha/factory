package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 20/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CyrelDocumentRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer version;
    private String documentUrl;
    private LocalDateTime versionDate;
}
