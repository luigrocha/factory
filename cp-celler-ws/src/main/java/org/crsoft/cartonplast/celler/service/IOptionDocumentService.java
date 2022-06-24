package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.celler.model.OptionDocument;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.OptionDocumentRes;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 2/6/2022
 */
public interface IOptionDocumentService {

    Collection<OptionDocumentRes> findAllByDocumentCode(Integer id) throws NotFoundException;

    OptionDocument findByCode(Integer code) throws NotFoundException;

}
