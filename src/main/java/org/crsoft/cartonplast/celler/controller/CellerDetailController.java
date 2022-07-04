
package org.crsoft.cartonplast.celler.controller;

import org.crsoft.cartonplast.celler.service.ICellerDetailService;
import org.crsoft.cartonplast.celler.vo.AllStockVo;
import org.crsoft.cartonplast.celler.vo.LoteStockVo;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.CellerDetailRes;
import org.crsoft.cartonplast.vo.res.CellerLoteRes;
import org.crsoft.cartonplast.vo.res.CellerStockRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@RestController
@RequestMapping(GlobalConstant.V1_API_VERSION + "/cellerDetail")
public class CellerDetailController {
    private final ICellerDetailService cellerDetailService;

    public CellerDetailController(ICellerDetailService cellerDetailService) {
        this.cellerDetailService = cellerDetailService;
    }

    @GetMapping("/findByLocationCode/{lote}/{codeMaterial}")
    public ResponseEntity<Collection<CellerDetailRes>> findByLocationCode(
            @PathVariable("lote") String lote,
            @PathVariable("codeMaterial") Integer codeMaterial) throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findByLocationCode(lote, codeMaterial));
    }

    @GetMapping("/findByMaterialCode/{code}")
    public ResponseEntity<Collection<CellerDetailRes>> findCellerByMaterialCode(@PathVariable("code") Integer code)
            throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findCellerDetailByMaterialCode(code));
    }

    @GetMapping("/findLoteByMaterialCode/{code}")
    public ResponseEntity<Collection<CellerLoteRes>> findLoteByMaterialCode(@PathVariable("code") Integer code)
            throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findLoteByMaterialCode(code));
    }

    @GetMapping("/findByCellerCode/{code}")
    public ResponseEntity<Collection<CellerDetailRes>> findCellerDetailByCellerCode(@PathVariable("code") Integer code)
            throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findCellerDetailByCellerCode(code));
    }

    @GetMapping("/findStock")
    public ResponseEntity<CellerStockRes> findCellerDetailStock(@RequestParam("materialCode") Integer materialCode
            , @RequestParam("lote") String lote) {
        return ResponseEntity.ok(this.cellerDetailService.findCellerDetailStock(materialCode, lote));
    }

    @GetMapping("/findByTypeMaterialStock/{typeCode}")
    public ResponseEntity<Collection<AllStockVo>> findByTypeMaterialStock(@PathVariable("typeCode") Integer typeCode)
            throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findByTypeMaterialStock(typeCode));
    }

    @GetMapping("/findAllStock")
    public ResponseEntity<Collection<AllStockVo>> findAllStock() throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findAllStock());
    }

    @GetMapping("/findMaterialStock/{materialCode}")
    public ResponseEntity<Collection<AllStockVo>> findMaterialStock(@PathVariable("materialCode") Integer materialCode)
            throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findMaterialStock(materialCode));
    }

    @GetMapping("/findMaterialLoteStock/{materialCode}/{lote}")
    public ResponseEntity<Collection<AllStockVo>> findMaterialLoteStock(
            @PathVariable("materialCode") Integer materialCode,
            @PathVariable("lote") String lote) throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findMaterialLoteStock(materialCode,lote));
    }

    @GetMapping("/findTotalStockByMaterial/{materialCode}")
    public ResponseEntity<Collection<AllStockVo>> findTotalStockByMaterial(
            @PathVariable("materialCode") Integer materialCode) throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findTotalStockByMaterial(materialCode));
    }

    @GetMapping("/findByMaterialStock/{materialCode}")
    public ResponseEntity<Collection<LoteStockVo>> findByMaterialStock(
            @PathVariable("materialCode") Integer materialCode) throws NotFoundException {
        return ResponseEntity.ok(this.cellerDetailService.findByMaterialStock(materialCode));
    }

}
