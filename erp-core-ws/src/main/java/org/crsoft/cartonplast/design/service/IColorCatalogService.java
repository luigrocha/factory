package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.req.ColorCatalogReq;
import org.crsoft.cartonplast.vo.res.ColorCatalogRes;

import java.util.Collection;

/**
 * @author jyepez on 30/5/2022
 */
public interface IColorCatalogService {

    Collection<ColorCatalogRes> findAllValidColors();

    ColorCatalogRes createColorCatalog(ColorCatalogReq colorCatalog);

    ColorCatalogRes findColorCatalogByCode(Integer code);

    ColorCatalogRes updateColorCatalogByCode(Integer code, ColorCatalogReq colorCatalog);

    boolean deleteColorCatalogByCode(Integer code);

}
