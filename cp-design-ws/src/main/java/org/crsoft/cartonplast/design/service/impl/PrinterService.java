package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.service.IPrinterService;
import org.crsoft.cartonplast.design.vo.res.PrinterRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Service
public class PrinterService implements IPrinterService {
    @Override
    public List<PrinterRes> findAllValidPrinters() {
        return null;
    }
}
