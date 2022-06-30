package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.celler.model.CellerDetail;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.req.CellerDetailReq;
import org.crsoft.cartonplast.vo.res.CellerDetailRes;
import org.crsoft.cartonplast.vo.res.CellerLoteRes;
import org.crsoft.cartonplast.vo.res.CellerStockRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ICellerDetailService {

    CellerDetail getCellarDetailByCode(Integer code) throws NotFoundException;

    Collection<CellerDetailRes> findByLocationCode(String lote, Integer codeMaterial) throws NotFoundException;

    Collection<CellerDetailRes> findCellerDetailByMaterialCode(Integer id) throws NotFoundException;

    Collection<CellerDetailRes> findCellerDetailByCellerCode(Integer code) throws NotFoundException;

    void createCellerDetail(Collection<CellerDetailReq> celler, Celler codeCeller, String userName) throws NotFoundException, InsertException;

    CellerStockRes findCellerDetailStock(Integer materialCode, String lote);

    Collection<CellerDetailRes> findByTypeMaterialStock(Integer typeCode);

    Collection<CellerLoteRes> findLoteByMaterialCode(Integer code) throws NotFoundException;
}
