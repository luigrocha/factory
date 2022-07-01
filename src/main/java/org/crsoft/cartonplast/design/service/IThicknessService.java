package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Thickness;
import org.crsoft.cartonplast.vo.res.ThicknessRes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IThicknessService {

    Collection<ThicknessRes> findAllValidThickness() throws NotFoundException;

    void createThickness(Thickness thickness) throws InsertException;

    ThicknessRes findThicknessByCode(Integer code) throws NotFoundException;

    void updateThicknessByCode(Integer code, Thickness thickness) throws NotFoundException, UpdateException;

    void deleteThicknessByCode(Integer code, String userName) throws NotFoundException, UpdateException;

}
