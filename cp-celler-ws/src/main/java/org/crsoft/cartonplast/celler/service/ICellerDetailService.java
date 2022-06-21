package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerRes;
import org.crsoft.cartonplast.vo.res.CodeDocumentRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ICellerDetailService {

    Collection<CellerRes> findAllCellerDetail() throws NotFoundException;

    Celler getCellarDetailByCode(Integer code) throws NotFoundException;

    Collection<CellerRes> findCellerDetailByMaterialCode(Integer id) throws NotFoundException;

    CodeDocumentRes findNewCodeDocumentByDocumentCode(Integer code) throws NotFoundException;

    void createCellerDetail(Collection<Celler> celler) throws NotFoundException, InsertException;
}
