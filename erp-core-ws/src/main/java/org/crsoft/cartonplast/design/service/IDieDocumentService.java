package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.res.DieDocumentRes;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * @author lpillaga on 20/06/2022
 */
public interface IDieDocumentService {

    List<DieDocumentRes> findAllValidDocumentsByDieId(Integer dieId);

    DieDocumentRes save(Integer dieId, MultipartFile file);

    DieDocumentRes update(Integer id, MultipartFile file);
}
