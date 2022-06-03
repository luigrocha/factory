package org.crsoft.cartonplast.design.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.model.Printer;
import org.crsoft.cartonplast.design.repository.PrinterRepository;
import org.crsoft.cartonplast.design.service.IPrinterService;
import org.crsoft.cartonplast.design.service.mapper.PrinterMapper;
import org.crsoft.cartonplast.design.vo.res.PrinterRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.*;

/**
 * @author lpillaga on 12/05/2022
 */
@Service
@Slf4j
public class PrinterService implements IPrinterService {

    private static final String TABLE_NAME = "CATIMP";
    private final PrinterRepository printerRepository;
    private final PrinterMapper printerMapper;

    public PrinterService(
            PrinterRepository printerRepository,
            PrinterMapper printerMapper) {
        this.printerRepository = printerRepository;
        this.printerMapper = printerMapper;
    }

    @Override
    public Collection<PrinterRes> findAllValidPrinters() throws NotFoundException {
        try {
            return this.printerMapper.printersToPrintersRes(
                    this.printerRepository.findAllByValidToIsNullOrderByNameAsc()
            );
        } catch (Exception e) {
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public void createPrinter(Printer printer) throws InsertException {
        try {
            this.printerRepository.save(printer);
        } catch (Exception e) {
            log.error("Error to createPrinter: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public PrinterRes findPrinterByCode(Integer code) throws NotFoundException {
        return this.printerMapper.printerToPrinterRes(getPrinterByCode(code));
    }

    @Override
    public void updatePrinterByCode(Integer code, Printer printer) throws NotFoundException, UpdateException {
        Printer printerByCode = getPrinterByCode(code);
        try {
            printerByCode.setName(printer.getName());
            printerByCode.setNumColors(printer.getNumColors());
            printerByCode.setDescription(printer.getDescription());
            printerByCode.setUpdatedAt(LocalDateTime.now());
            printerByCode.setUpdatedBy(printer.getUpdatedBy());
            this.printerRepository.save(printerByCode);
        } catch (Exception e) {
            log.error("Error to updatePrinterByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_UPDATE);
        }
    }

    @Override
    public void deletePrinterByCode(Integer code, String userName) throws NotFoundException, UpdateException {
        Printer printer = getPrinterByCode(code);
        try {
            printer.setUpdatedBy(userName);
            printer.setUpdatedAt(LocalDateTime.now());
            printer.setValidTo(LocalDateTime.now());
            this.printerRepository.save(printer);
        } catch (Exception e) {
            log.error("Error to deletePrinterByCode: {}", e.getMessage());
            throw new UpdateException(TABLE_NAME, MESSAGE_DELETE);
        }
    }

    private Printer getPrinterByCode(Integer code) throws NotFoundException {
        Optional<Printer> printer = this.printerRepository.findByIdAndValidToIsNull(code);
        if (printer.isPresent()) {
            return printer.get();
        } else {
            log.info("Error to getPrinterByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
