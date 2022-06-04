package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.crsoft.cartonplast.vo.res.CodeDocumentRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ICellerService {

    Collection<CellerRes> findAllCeller() throws NotFoundException;

    Celler getCellarByCode(Integer code) throws NotFoundException;

    Collection<CellerRes> findCellerByMaterialCode(Integer id) throws NotFoundException;

    CodeDocumentRes findNewCodeDocumentByDocumentCode(Integer code) throws NotFoundException;

    void createCeller(Collection<Celler> celler) throws NotFoundException, InsertException;
}
