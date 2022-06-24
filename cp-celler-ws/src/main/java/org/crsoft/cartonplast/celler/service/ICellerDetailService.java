package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.CellerDetail;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.CellerDetailReq;
import org.crsoft.cartonplast.vo.res.CellerDetailRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ICellerDetailService {

    CellerDetail getCellarDetailByCode(Integer code) throws NotFoundException;

    Collection<CellerDetailRes> findByLocationCode(Integer code, Integer codeMaterial) throws NotFoundException;

    Collection<CellerDetailRes> findCellerDetailByMaterialCode(Integer id) throws NotFoundException;

    Collection<CellerDetailRes> findCellerDetailByCellerCode(Integer code) throws NotFoundException;

    void createCellerDetail(Collection<CellerDetailReq> celler, Celler codeCeller, String userName) throws NotFoundException, InsertException;
}
