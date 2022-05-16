package org.crsoft.cartonplast.design.service.impl;

import org.crsoft.cartonplast.design.repository.PrinterRepository;
import org.crsoft.cartonplast.design.service.IPrinterService;
import org.crsoft.cartonplast.design.service.mapper.PrinterMapper;
import org.crsoft.cartonplast.design.vo.res.PrinterRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Service
public class PrinterService implements IPrinterService {

    private final PrinterRepository printerRepository;
    private final PrinterMapper printerMapper;

    public PrinterService(
            PrinterRepository printerRepository,
            PrinterMapper printerMapper) {
        this.printerRepository = printerRepository;
        this.printerMapper = printerMapper;
    }

    @Override
    public List<PrinterRes> findAllValidPrinters() {
        return this.printerMapper.printersToPrintersRes(
                this.printerRepository.findAllValidPrinters()
        );
    }
}
