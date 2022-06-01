package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.DocumentRes;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
public interface IDocumentService {

    Collection<DocumentRes> findAllDocument() throws NotFoundException;

}
