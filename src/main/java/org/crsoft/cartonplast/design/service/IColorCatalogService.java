package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.ColorCatalog;
import org.crsoft.cartonplast.vo.res.ColorCatalogRes;

import java.util.Collection;

/**
 * @author jyepez on 30/5/2022
 */
public interface IColorCatalogService {

    Collection<ColorCatalogRes> findAllValidColors() throws NotFoundException;

    void createColorCatalog(ColorCatalog colorCatalog) throws InsertException;

    ColorCatalogRes findColorCatalogByCode(Integer code) throws NotFoundException;

    void updateColorCatalogByCode(Integer code, ColorCatalog colorCatalog) throws NotFoundException, UpdateException;

    void deleteColorCatalogByCode(Integer code, String userName) throws NotFoundException, UpdateException;

}
