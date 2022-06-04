package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 3/6/2022
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CodeDocumentRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private String document;
    private Integer number;
    private String numDocument;
}
