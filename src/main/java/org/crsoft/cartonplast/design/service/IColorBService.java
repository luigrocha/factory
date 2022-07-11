package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.model.ColorB;
import org.crsoft.cartonplast.vo.req.ColorBReq;
import org.crsoft.cartonplast.vo.res.ColorBRes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IColorBService {

    Collection<ColorBRes> findAllValidColors();

    ColorBRes createColorB(ColorBReq colorB);

    ColorBRes findColorBByCode(String code);

    ColorBRes updateColorBByCode(String code, ColorBReq colorB);

    boolean deleteColorBByCode(String code);

    ColorB getColorBById(String id);
}
