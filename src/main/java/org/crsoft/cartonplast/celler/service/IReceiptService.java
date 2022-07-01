package org.crsoft.cartonplast.celler.service;

import org.crsoft.cartonplast.vo.req.GenerateReceiptReq;

/**
 * @author lpillaga on 04/06/2022
 */
public interface IReceiptService {

    String getReceiptType();

    byte[] generateReceipt(GenerateReceiptReq generateReceiptReq);
}
