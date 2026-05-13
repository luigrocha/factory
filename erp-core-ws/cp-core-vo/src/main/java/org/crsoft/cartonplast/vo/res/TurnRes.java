package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalTime;

/**
 * @author lpillaga on 15/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TurnRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private LocalTime startTime;
    private LocalTime endTime;
    private Boolean isActive;
}
