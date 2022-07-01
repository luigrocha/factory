package org.crsoft.cartonplast.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 12/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientShortRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String code;
    private String name;
    private String imageUrl;
    private String imageName;
}
