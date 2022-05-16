package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.res.ColorARes;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IColorAService {

    List<ColorARes> findAllValidColors();
}
