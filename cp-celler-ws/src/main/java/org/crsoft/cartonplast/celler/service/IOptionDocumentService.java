package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.OptionDocumentRes;

import java.util.Collection;

/**
 * @author jyepez on 2/6/2022
 */
public interface IOptionDocumentService {

    Collection<OptionDocumentRes> findAllByDocumentCode(Integer id) throws NotFoundException;

}
