package org.crsoft.cartonplast.design.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.client.vo.res.ClientShortRes;

import java.io.Serializable;
import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CyrelRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String print;
    private String description;
    private String description2;
    private String observation;
    private DieShortRes die;
    private PrinterShortRes printer;
    private ColorBShortRes mbLeaf;
    private ClientShortRes client;
    private List<CyrelColorRes> cyrelColors;
}
