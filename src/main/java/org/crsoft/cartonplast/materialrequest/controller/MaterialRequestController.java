package org.crsoft.cartonplast.materialrequest.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.materialrequest.service.IMaterialRequestService;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.MaterialRequestRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
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
}
