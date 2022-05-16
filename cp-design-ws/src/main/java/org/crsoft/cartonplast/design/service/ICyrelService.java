package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


/**
 * @author lpillaga on 12/05/2022
 */
public interface ICyrelService {

    Page<CyrelRes> findAllValidCyrels(Pageable pageable, String query);
}
