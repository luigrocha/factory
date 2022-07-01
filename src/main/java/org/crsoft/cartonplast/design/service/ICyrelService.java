package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.req.CyrelReq;
import org.crsoft.cartonplast.vo.res.CyrelRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author lpillaga on 12/05/2022
 */
public interface ICyrelService {

    Page<CyrelRes> findAllValidCyrels(Pageable pageable, String query);

    CyrelRes createCyrel(CyrelReq cyrelReq);
}
