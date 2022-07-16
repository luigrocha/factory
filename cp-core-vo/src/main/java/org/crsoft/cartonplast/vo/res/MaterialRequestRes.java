package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 15/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class MaterialRequestRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String number;
    private LocalDateTime date;
    private String documentByUsername;
    private String documentBy;
    private CatalogStatusRes status;
    private TurnRes turn;
}
