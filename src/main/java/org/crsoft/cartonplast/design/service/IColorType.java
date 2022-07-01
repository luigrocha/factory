package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.res.ColorTypeRes;

import java.util.List;

/**
 * @author lpillaga on 17/06/2022
 */
public interface IColorType {

    List<ColorTypeRes> findAllValidColorTypes();
}
