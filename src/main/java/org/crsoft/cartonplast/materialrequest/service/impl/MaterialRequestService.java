package org.crsoft.cartonplast.materialrequest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.catalog.service.ICatalogStatusService;
import org.crsoft.cartonplast.common.filter.SpecificationBuilder;
import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.crsoft.cartonplast.materialrequest.repository.MaterialRequestRepository;
import org.crsoft.cartonplast.materialrequest.repository.specification.MaterialRequestSpecification;
import org.crsoft.cartonplast.materialrequest.service.IMaterialRequestService;
import org.crsoft.cartonplast.materialrequest.service.mapper.MaterialRequestMapper;
import org.crsoft.cartonplast.vo.req.MaterialRequestReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.MaterialRequestRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class MaterialRequestService implements IMaterialRequestService {

    private final MaterialRequestRepository materialRequestRepository;
    private final SpecificationBuilder<MaterialRequest> specificationBuilder;
    private final MaterialRequestMapper materialRequestMapper;
    private final ICatalogStatusService catalogStatusService;

    @Override
    public Page<MaterialRequestRes> findAllValidMaterialRequests(
            List<SearchCriteriaReq> searchCriteria,
            Pageable pageable,
            List<String> states,
            String query) {
        List<CatalogStatus> catalogStatuses = this.catalogStatusService.findByIds(states);

        Specification<MaterialRequest> materialRequestSpecification =
                specificationBuilder.buildSpecification(searchCriteria);

        Page<MaterialRequest> materials = materialRequestRepository.findAll(
                Specification.where(materialRequestSpecification)
                        .and(MaterialRequestSpecification.filterByStates(catalogStatuses))
                        .and(MaterialRequestSpecification.filterByQuery(query)),
                pageable);

        return materials.map(materialRequestMapper::toMaterialRequestRes);
    }

    @Override
    public long findCountMaterialRequest() {
        MaterialRequest materialRequest = this.materialRequestRepository.findLast();
        String number = materialRequest.getNumber().split("-")[1];
        return Integer.parseInt(number) + 1;
    }

    @Override
    public void create(MaterialRequestReq materialRequestReq) {
        boolean existsByNumber = this.materialRequestRepository.existsByNumber(materialRequestReq.getNumber());
        if(existsByNumber){
            MaterialRequest materialRequestFind = this.materialRequestRepository.findByNumber(materialRequestReq.getNumber());
            MaterialRequest materialRequest = this.materialRequestMapper.toMaterialRequest(materialRequestReq);
            materialRequest.setId(materialRequestFind.getId());
            materialRequest.setDate(materialRequestFind.getDate());
            this.materialRequestRepository.save(materialRequest);
        }else{
            this.materialRequestRepository.save(this.materialRequestMapper.toMaterialRequest(materialRequestReq));
        }
    }
}
