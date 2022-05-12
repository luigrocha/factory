package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.res.ThicknessRes;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IThicknessService {

    List<ThicknessRes> findAllValidThickness();
}
