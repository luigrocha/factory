package org.crsoft.cartonplast.mixture.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.util.HttpUtil;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.mixture.service.IMixtureService;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureMapper;
import org.crsoft.cartonplast.vo.req.GenerateMixtureReceiptReq;
import org.crsoft.cartonplast.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.vo.req.MixtureReq;
import org.crsoft.cartonplast.vo.res.MixtureRes;
import org.crsoft.cartonplast.vo.res.MixtureShortRes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author jyepez on 3/7/2022
 */
@RestController
@RequiredArgsConstructor
@RequestMapping(GlobalConstant.V1_API_VERSION + "/mixture")
public class MixtureController {
    private final IMixtureService mixtureService;
    private final MixtureMapper mixtureMapper;

    @GetMapping("/findNumberToCreate")
    public ResponseEntity<Long> findNumber() {
        return ResponseEntity.ok(mixtureService.findNumber() + 1);
    }

    @PostMapping()
    public ResponseEntity<?> create(@RequestBody MixtureReq mixtureReq)
            throws InsertException {
        Mixture mixture = this.mixtureMapper.mixtureResToMixture(mixtureReq);
        this.mixtureService.create(mixture,mixtureReq.getRows());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/search")
    public ResponseEntity<Collection<MixtureShortRes>> findAllMixture(@RequestParam(value = "query") String query){
        return ResponseEntity.ok(mixtureService.findByQuery(query));
    }

    @GetMapping("/search/{number}")
    public ResponseEntity<MixtureRes> findMixtureByNumber(@PathVariable("number") Integer number) {
        return ResponseEntity.ok(mixtureService.findByNumber(number));
    }

    @GetMapping("/findNumberByLot/{lot}")
    public ResponseEntity<Integer> findNumberByLot(@PathVariable("lot") String lot){
        return ResponseEntity.ok(mixtureService.findNumberByLot(lot));
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> editMixture(@PathVariable("id") Integer id, @RequestBody MixtureReq mixtureReq){
        Mixture mixture = this.mixtureMapper.mixtureResToMixture(mixtureReq);
        this.mixtureService.edit(id, mixture, mixtureReq.getRows());
        return ResponseEntity.ok().build();
    }

    @GetMapping(value = "/get-receipt/{mixtureId}", produces = "application/pdf")
    public ResponseEntity<byte[]> getReceipt(
            @PathVariable("mixtureId") Integer mixtureId){
        GenerateMixtureReceiptReq receiptReq = this.mixtureService.generateReceiptData(mixtureId);
        byte[] pdf = mixtureService.generateReceipt(receiptReq);
        return new ResponseEntity<>(
                pdf,
                HttpUtil.getDefaultPDFHeaders(receiptReq.getNumber()),
                HttpStatus.OK);
    }
}
