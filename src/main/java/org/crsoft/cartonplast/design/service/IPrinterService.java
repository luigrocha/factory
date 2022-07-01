package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Printer;
import org.crsoft.cartonplast.vo.res.PrinterRes;

import java.util.Collection;

/**
 * @author lpillaga on 12/05/2022
 */
public interface IPrinterService {

    Collection<PrinterRes> findAllValidPrinters() throws NotFoundException;

    void createPrinter(Printer printer) throws InsertException;

    PrinterRes findPrinterByCode(Integer code) throws NotFoundException;

    void updatePrinterByCode(Integer code, Printer printer) throws NotFoundException, UpdateException;

    void deletePrinterByCode(Integer code, String userName) throws NotFoundException, UpdateException;

}
