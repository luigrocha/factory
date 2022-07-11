package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Printer;
import org.crsoft.cartonplast.vo.req.PrinterReq;
import org.crsoft.cartonplast.vo.res.PrinterRes;
import org.crsoft.cartonplast.vo.res.PrinterShortRes;
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

    @WithoutAuditField
    Printer printerReqToPrinter(PrinterReq printerReq);

    Collection<PrinterRes> printersToPrintersRes(Collection<Printer> printers);
}
