package org.crsoft.cartonplast.materialrequest.service;

import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.crsoft.cartonplast.vo.req.GenerateMaterialRequestReceiptReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.MaterialRequestRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
public interface IMaterialRequestService {

    Page<MaterialRequestRes> findAllValidMaterialRequests(List<SearchCriteriaReq> searchCriteria, Pageable pageable, List<String> states, String query);

    MaterialRequest findById(Integer id);

    GenerateMaterialRequestReceiptReq generateReceiptData(Integer id);

    byte[] generateReceipt(GenerateMaterialRequestReceiptReq req);
}
