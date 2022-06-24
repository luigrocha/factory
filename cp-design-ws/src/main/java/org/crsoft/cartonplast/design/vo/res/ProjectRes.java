package org.crsoft.cartonplast.design.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.client.vo.res.ClientShortRes;

import java.io.Serializable;

/**
 * @author lpillaga on 23/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProjectRes implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private String codeGen;
    private Integer gsm;
    private Boolean aydProcess;
    private Boolean isStructural;
    private Boolean hasCrown;
    private String leafName;
    private String referenceTag;
    private Boolean hasLogo;
    private Integer quantityXPackage;
    private ClientShortRes client;
    private HomoPolymerRes homoPolymer;
    private ColorBShortRes colorB;
    private CyrelShortRes cyrel;
    private TalcRes talc;
    private CalciumCarbonateRes calciumCarbonate;
    private DieProductShortRes dieProduct;
    private ProjectTypeRes projectType;
}
