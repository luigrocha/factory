package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorB;
import org.crsoft.cartonplast.design.vo.res.ColorBRes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IColorBService {

    Collection<ColorBRes> findAllValidColors() throws NotFoundException;

    void createColorB(ColorB colorB) throws InsertException;

    ColorBRes findColorBByCode(String code) throws NotFoundException;

    void updateColorBByCode(String code, ColorB colorB) throws NotFoundException, UpdateException;

    void deleteColorBByCode(String code, String userName) throws NotFoundException, UpdateException;

}
