package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.req.ThicknessReq;
import org.crsoft.cartonplast.vo.res.ThicknessRes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IThicknessService {

    Collection<ThicknessRes> findAllValidThickness();

    ThicknessRes createThickness(ThicknessReq thickness);

    ThicknessRes findThicknessByCode(Integer code);

    ThicknessRes updateThicknessByCode(Integer code, ThicknessReq thickness);

    boolean deleteThicknessByCode(Integer code);

}
