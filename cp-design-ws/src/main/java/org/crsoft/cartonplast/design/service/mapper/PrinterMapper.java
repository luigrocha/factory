package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Printer;
import org.crsoft.cartonplast.design.vo.res.PrinterRes;
import org.crsoft.cartonplast.design.vo.res.PrinterShortRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

@Mapper(componentModel = "spring")
public interface PrinterMapper {

    PrinterRes printerToPrinterRes(Printer printer);

    @WithoutAuditField
    @Mapping(target = "cyrels", ignore = true)
    Printer printerResToPrinter(PrinterRes printerRes);

    PrinterShortRes printerToPrinterShortRes(Printer printer);

    Collection<PrinterRes> printersToPrintersRes(Collection<Printer> printers);
}
