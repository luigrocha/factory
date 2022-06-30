package org.crsoft.cartonplast.printer;

import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.common.exception.UpdateException;
import org.crsoft.cartonplast.design.service.impl.PrinterService;
import org.crsoft.cartonplast.design.vo.res.PrinterRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 11/05/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/printers")
public class PrinterController {

    private final PrinterService printerService;

    public PrinterController(PrinterService printerService) {
        this.printerService = printerService;
    }

    @GetMapping
    public ResponseEntity<Collection<PrinterRes>> getAllPrinters() throws NotFoundException {
        return ResponseEntity.ok(this.printerService.findAllValidPrinters());
    }

    @PostMapping
    public ResponseEntity<?> createPrinter(@RequestBody Printer printer, @RequestHeader("userName") String userName) throws InsertException {
        printer.setCreatedBy(userName);
        this.printerService.createPrinter(printer);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{code}")
    public ResponseEntity<PrinterRes> findPrinterByCode(@PathVariable("code") Integer code) throws NotFoundException {
        return ResponseEntity.ok().body(this.printerService.findPrinterByCode(code));
    }

    @PatchMapping("/{code}")
    public ResponseEntity<?> updatePrinterByCode(@PathVariable("code") Integer code, @RequestBody Printer printer, @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        printer.setUpdatedBy(userName);
        this.printerService.updatePrinterByCode(code, printer);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{code}")
    public ResponseEntity<?> deletePrinterByCode(@PathVariable("code") Integer code, @RequestHeader("userName") String userName) throws NotFoundException, UpdateException {
        this.printerService.deletePrinterByCode(code, userName);
        return ResponseEntity.ok().build();
    }

}
