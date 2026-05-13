package org.crsoft.cartonplast.materialrequest.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.catalog.service.ICatalogStatusService;
import org.crsoft.cartonplast.celler.util.ReceiptGeneratorUtil;
import org.crsoft.cartonplast.common.constant.ReceiptConstant;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.common.filter.SpecificationBuilder;
import org.crsoft.cartonplast.common.util.DateUtil;
import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.crsoft.cartonplast.materialrequest.repository.MaterialRequestRepository;
import org.crsoft.cartonplast.materialrequest.repository.specification.MaterialRequestSpecification;
import org.crsoft.cartonplast.materialrequest.service.IMaterialRequestService;
import org.crsoft.cartonplast.materialrequest.service.mapper.MaterialRequestMapper;
import org.crsoft.cartonplast.vo.req.MaterialRequestReq;
import org.crsoft.cartonplast.vo.req.GenerateMaterialRequestReceiptItemReq;
import org.crsoft.cartonplast.vo.req.GenerateMaterialRequestReceiptReq;
import org.crsoft.cartonplast.vo.req.SearchCriteriaReq;
import org.crsoft.cartonplast.vo.res.MaterialRequestRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
    private final ReceiptGeneratorUtil receiptGeneratorUtil;

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

    @Override
    public MaterialRequest findById(Integer id) {
        return materialRequestRepository.findById(id)
                .orElseThrow(() -> {
                    log.error("MaterialRequest not found by id: {}", id);
                    return new BusinessException(BusinessExceptionReason.MATERIAL_REQUEST_NOT_FOUND, id);
                });
    }

    @Override
    public GenerateMaterialRequestReceiptReq generateReceiptData(Integer id) {
        MaterialRequest materialRequest = this.findById(id);

        List<GenerateMaterialRequestReceiptItemReq> items = materialRequest.getItems().stream()
                .map(material -> GenerateMaterialRequestReceiptItemReq.builder()
                        .type(material.getMaterial().getTypeMaterial().getDescription())
                        .product(material.getMaterial().getName())
                        .balance(material.getBalance())
                        .coat(material.getCoat())
                        .pallets(material.getPallets())
                        .weight(material.getWeight())
                        .coatNumber(material.getCoatNumber())
                        .palletNumber(material.getPalletNumber())
                        .observation(material.getObservation())
                        .build()).collect(Collectors.toList());

        return GenerateMaterialRequestReceiptReq.builder()
                .number(materialRequest.getNumber())
                .responsible(materialRequest.getDocumentBy())
                .date(materialRequest.getDate())
                .turn(materialRequest.getTurn().getName())
                .items(items)
                .build();
    }

    @Override
    public byte[] generateReceipt(GenerateMaterialRequestReceiptReq receiptReq) {
        try {
            JasperReport jasperReport = receiptGeneratorUtil.getReportFromResources(ReceiptConstant.MATERIAL_REQUEST_JRXML_NAME);
            final Map<String, Object> parameters = receiptGeneratorUtil.getReportCommonData();
            parameters.put("number", receiptReq.getNumber());
            parameters.put("responsible", receiptReq.getResponsible());
            parameters.put("turn", receiptReq.getTurn());
            parameters.put("date", DateUtil.generateReceiptDateTime(receiptReq.getDate()));
            parameters.put("itemsDataSource", new JRBeanCollectionDataSource(receiptReq.getItems()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            if (jasperPrint == null) {
                throw new BusinessException(BusinessExceptionReason.MATERIAL_REQUEST_RECEIPT_FAILED);
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            log.error("Error to generate receipt: {}", e.getMessage());
            throw new BusinessException(BusinessExceptionReason.MATERIAL_REQUEST_RECEIPT_FAILED);
        }
    }
}
