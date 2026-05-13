package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.crsoft.cartonplast.vo.enums.SearchOperation;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;

/**
 * @author lpillaga on 03/07/2022
 */
@Data
@AllArgsConstructor
public class SearchCriteriaReq implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotEmpty
    private String key;

    @NotNull
    private Object value;

    @NotNull
    private SearchOperation operation;

    private List<String> values;
}
