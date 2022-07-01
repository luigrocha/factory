package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorA;
import org.crsoft.cartonplast.vo.res.ColorARes;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
public interface IColorAService {

    Collection<ColorARes> findAllValidColors() throws NotFoundException;

    void createColorA(ColorA color) throws InsertException;

    ColorARes findColorAByCode(String code) throws NotFoundException;

    void updateColorAByCode(String code, ColorA color) throws NotFoundException, UpdateException;

    void deleteColorAByCode(String code, String userName) throws NotFoundException, UpdateException;

    ColorA getColorAById(String id) throws NotFoundException;
}
