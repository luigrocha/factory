package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.req.PrinterReq;
import org.crsoft.cartonplast.vo.res.PrinterRes;

import java.util.Collection;

/**
 * @author lpillaga on 12/05/2022
 */
public interface IPrinterService {

    Collection<PrinterRes> findAllValidPrinters();

    PrinterRes createPrinter(PrinterReq printer);

    PrinterRes findPrinterByCode(Integer code);

    PrinterRes updatePrinterByCode(Integer code, PrinterReq printer);

    boolean deletePrinterByCode(Integer code);
}
