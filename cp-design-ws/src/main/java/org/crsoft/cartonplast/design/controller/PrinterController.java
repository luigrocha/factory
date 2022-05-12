package org.crsoft.cartonplast.design.controller;

import org.crsoft.cartonplast.design.service.impl.PrinterService;
import org.crsoft.cartonplast.design.vo.res.PrinterRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
    public ResponseEntity<List<PrinterRes>> getAllPrinters() {
        return ResponseEntity.ok(this.printerService.findAllValidPrinters());
    }
}
