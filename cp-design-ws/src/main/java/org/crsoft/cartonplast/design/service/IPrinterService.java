package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.res.PrinterRes;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
public interface IPrinterService {

    List<PrinterRes> findAllValidPrinters();
}
