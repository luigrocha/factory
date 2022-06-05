package org.crsoft.cartonplast.celler.service.impl;

import org.crsoft.cartonplast.celler.service.IReceiptService;
import org.crsoft.cartonplast.common.exception.ReceiptGeneratorException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lpillaga on 05/06/2022
 */
@Service
public class ReceiptServiceFactory {

    private static final Map<String, IReceiptService> receiptServiceMap = new HashMap<>();

    @Autowired
    private ReceiptServiceFactory(List<IReceiptService> receiptServices) {
        receiptServices.forEach(receiptService -> receiptServiceMap.put(receiptService.getReceiptType(), receiptService));
    }

    public static IReceiptService getService(String type) {
        IReceiptService service = receiptServiceMap.get(type);

        if (service == null) {
            throw new ReceiptGeneratorException("El comprobante aún no ha sido desarrollado: " + type);
        }

        return service;
    }
}
