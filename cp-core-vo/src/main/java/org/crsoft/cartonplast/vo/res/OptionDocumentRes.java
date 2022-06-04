package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author jyepez on 1/6/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OptionDocumentRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String description;
    private DocumentRes document;
}
