package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.Printer;
import org.crsoft.cartonplast.design.vo.res.PrinterRes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PrinterMapper {

    PrinterRes printerToPrinterRes(Printer printer);

    Printer printerResToPrinter(PrinterRes printerRes);

    List<PrinterRes> printersToPrintersRes(List<Printer> printers);
}
