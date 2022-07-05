package org.crsoft.cartonplast.mixture.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.mixture.model.MixtureDetail;
import org.crsoft.cartonplast.vo.req.MixtureDetailReq;

import java.util.Collection;

/**
 * @author jyepez on 3/7/2022
 */
public interface IMixtureService {

    long findNumber();

    void create(Mixture mixture, Collection<MixtureDetailReq> mixtureDetailsReq) throws InsertException;

}
