package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DieDocumentRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private Integer version;
    private String documentUrl;
    private LocalDateTime versionDate;
}
