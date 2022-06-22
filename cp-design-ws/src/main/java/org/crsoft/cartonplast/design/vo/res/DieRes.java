package org.crsoft.cartonplast.design.vo.res;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.vo.res.CatalogStatusRes;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

/**
 * @author lpillaga on 30/04/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DieRes implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;
    private String name;
    private LocalDate createdDate;
    private String dsbMultiple;
    private String observations;
    private Double quantity;
    private Double quantityLength;
    private Double separationLength;
    private Double quantityWidth;
    private Double separationWidth;
    private Double borderLength;
    private Double borderWidth;
    private Double leafLength;
    private Double leafWidth;
    private String manufacturer;
    private DieProductRes dieProduct;
    private CatalogStatusRes status;
    private List<String> machines;
    private List<DieDocumentRes> documents;
}
