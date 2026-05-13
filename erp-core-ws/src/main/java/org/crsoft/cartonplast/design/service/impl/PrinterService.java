package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.design.model.Printer;
import org.crsoft.cartonplast.design.repository.PrinterRepository;
import org.crsoft.cartonplast.design.service.IPrinterService;
import org.crsoft.cartonplast.design.service.mapper.PrinterMapper;
import org.crsoft.cartonplast.vo.req.PrinterReq;
import org.crsoft.cartonplast.vo.res.PrinterRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 12/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PrinterService implements IPrinterService {

    private final PrinterRepository printerRepository;
    private final PrinterMapper printerMapper;

    @Override
    public Collection<PrinterRes> findAllValidPrinters() {
        return this.printerMapper.printersToPrintersRes(
                this.printerRepository.findAllByValidToIsNullOrderByNameAsc()
        );
    }

    @Override
    @Transactional
    public PrinterRes createPrinter(PrinterReq printerReq) {
        return this.printerMapper.printerToPrinterRes(
                this.printerRepository.save(this.printerMapper.printerReqToPrinter(printerReq))
        );
    }

    @Override
    public PrinterRes findPrinterByCode(Integer code) {
        return this.printerMapper.printerToPrinterRes(getPrinterByCode(code));
    }

    @Override
    @Transactional
    public PrinterRes updatePrinterByCode(Integer code, PrinterReq printerReq) {
        Printer printerByCode = getPrinterByCode(code);
        printerByCode.setName(printerReq.getName());
        printerByCode.setNumColors(printerReq.getNumColors());
        printerByCode.setDescription(printerReq.getDescription());

        return this.printerMapper.printerToPrinterRes(printerRepository.save(printerByCode));
    }

    @Override
    @Transactional
    public boolean deletePrinterByCode(Integer code) {
        return printerRepository.findById(code)
                .map(machine -> {
                    machine.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    private Printer getPrinterByCode(Integer code) {
        Optional<Printer> printer = this.printerRepository.findByIdAndValidToIsNull(code);
        if (printer.isPresent()) {
            return printer.get();
        } else {
            log.info("Error to getPrinterByCode {}", code);
            throw new BusinessException(BusinessExceptionReason.PRINTER_NOT_FOUND, code);
        }
    }
}
