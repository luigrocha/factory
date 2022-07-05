package org.crsoft.cartonplast.mixture.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.mixture.model.MixtureDetail;

import java.util.Collection;

/**
 * @author jyepez on 5/7/2022
 */
public interface IMixtureDetailService {

    void create(MixtureDetail mixtureDetail) throws InsertException;

    void createAll(Collection<MixtureDetail> mixtureDetails) throws InsertException;
}
