package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface ICellerService {

    Collection<CellerRes> findAllCeller() throws NotFoundException;

}
