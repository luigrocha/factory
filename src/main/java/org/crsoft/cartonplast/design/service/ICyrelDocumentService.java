package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.res.CyrelDocumentRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lpillaga on 20/06/2022
 */
public interface ICyrelDocumentService {

    List<CyrelDocumentRes> findAllValidDocumentsByCyrelId(Integer cyrelId);

    CyrelDocumentRes save(Integer cyrelId, MultipartFile file);

    CyrelDocumentRes update(Integer id, MultipartFile file);
}
