package org.crsoft.cartonplast.materialrequest.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.util.HttpUtil;
import org.crsoft.cartonplast.materialrequest.service.IMaterialRequestService;
import org.crsoft.cartonplast.vo.req.GenerateMaterialRequestReceiptReq;
import org.crsoft.cartonplast.vo.req.MaterialRequestReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.MaterialRequestRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

import static org.crsoft.cartonplast.common.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 15/07/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/material-requests")
@RequiredArgsConstructor
@Slf4j
public class MaterialRequestController {

    private final IMaterialRequestService materialRequestService;

    @PostMapping("/search")
    public ResponseEntity<Page<MaterialRequestRes>> findAllMaterialRequests(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam List<String> states,
            @RequestParam(required = false) String query,
            @Valid @RequestBody List<SearchCriteriaReq> searchCriteria){
        Pageable paging = PageRequest.of(page, size);

        return ResponseEntity.ok(materialRequestService.findAllValidMaterialRequests(
                searchCriteria, paging, states, query));
    }

    @GetMapping("/count")
    public ResponseEntity<Long> findCountMaterialRequest(){
        return ResponseEntity.ok(materialRequestService.findCountMaterialRequest());
    }

    @PostMapping
    public ResponseEntity<?> create(@RequestBody MaterialRequestReq materialRequestReq){
        materialRequestService.create(materialRequestReq);
        return ResponseEntity.ok().build();
    }


    @GetMapping(value = "/get-receipt/{id}", produces = "application/pdf")
    public ResponseEntity<byte[]> getReceipt(
            @PathVariable("id") Integer id){
        GenerateMaterialRequestReceiptReq receiptReq = this.materialRequestService.generateReceiptData(id);
        byte[] pdf = materialRequestService.generateReceipt(receiptReq);
        return new ResponseEntity<>(
                pdf,
                HttpUtil.getDefaultPDFHeaders(receiptReq.getNumber()),
                HttpStatus.OK);
    }
}
