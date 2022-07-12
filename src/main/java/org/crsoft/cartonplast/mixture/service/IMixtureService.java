package org.crsoft.cartonplast.mixture.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.vo.req.GenerateMixtureReceiptReq;
import org.crsoft.cartonplast.vo.req.MixtureDetailReq;
import org.crsoft.cartonplast.vo.res.MixtureRes;
import org.crsoft.cartonplast.vo.res.MixtureShortRes;

import java.util.Collection;

/**
 * @author jyepez on 3/7/2022
 */
public interface IMixtureService {

    long findNumber();

    void create(Mixture mixture, Collection<MixtureDetailReq> mixtureDetailsReq) throws InsertException;

    Collection<MixtureShortRes> findByQuery(String query);

    MixtureRes findByNumber(Integer number);

    Integer findNumberByLot(String lote);

    void edit(Integer id, Mixture mixture, Collection<MixtureDetailReq> mixtureDetailsReq);

    byte[] generateReceipt(GenerateMixtureReceiptReq receiptReq);

    GenerateMixtureReceiptReq generateReceiptData(Integer mixtureId);
}
