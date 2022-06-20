package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.req.CyrelReq;
import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.web.multipart.MultipartFile;


/**
 * @author lpillaga on 12/05/2022
 */
public interface ICyrelService {

    Page<CyrelRes> findAllValidCyrels(Pageable pageable, String query);

    CyrelRes createCyrel(CyrelReq cyrelReq);

    CyrelRes uploadCyrelDocument(Integer id, MultipartFile file);
}
