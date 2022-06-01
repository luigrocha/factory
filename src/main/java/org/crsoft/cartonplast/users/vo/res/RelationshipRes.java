package org.crsoft.cartonplast.users.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RelationshipRes implements Serializable {

    private static final long serialVersionUID = -81207908739079087L;

    private String id;
    private String name;
}
