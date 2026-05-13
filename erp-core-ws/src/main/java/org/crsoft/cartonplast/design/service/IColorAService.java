package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.vo.req.ColorAReq;
import org.crsoft.cartonplast.vo.res.ColorARes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IColorAService {

    Collection<ColorARes> findAllValidColors();

    ColorARes createColorA(ColorAReq color);

    ColorARes findColorAByCode(String code);

    ColorARes updateColorAByCode(String code, ColorAReq color);

    boolean deleteColorAByCode(String code);

    ColorA getColorAById(String id);
}
