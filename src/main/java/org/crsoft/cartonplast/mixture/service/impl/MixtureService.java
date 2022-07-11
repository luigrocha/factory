package org.crsoft.cartonplast.mixture.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crsoft.cartonplast.celler.util.ReceiptGeneratorUtil;
import org.crsoft.cartonplast.common.constant.DesignConstant;
import org.crsoft.cartonplast.common.constant.ReceiptConstant;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.common.exception.InsertException;
import org.crsoft.cartonplast.common.util.DateUtil;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.mixture.model.MixtureDetail;
import org.crsoft.cartonplast.mixture.repository.MixtureRepository;
import org.crsoft.cartonplast.mixture.service.IMixtureService;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureDetailMapper;
import org.crsoft.cartonplast.mixture.service.mapper.MixtureMapper;
import org.crsoft.cartonplast.vo.req.GenerateMixtureReceiptItemReq;
import org.crsoft.cartonplast.vo.req.GenerateMixtureReceiptReq;
import org.crsoft.cartonplast.vo.req.MixtureDetailReq;
import org.crsoft.cartonplast.vo.res.MixtureRes;
import org.crsoft.cartonplast.vo.res.MixtureShortRes;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.List;
import java.util.Map;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_INSERT;
import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 3/7/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class MixtureService implements IMixtureService {

    private static final String TABLE_NAME = "CETMIX";
    private final MixtureRepository mixtureRepository;
    private final MixtureMapper mixtureMapper;
    private final MixtureDetailMapper mixtureDetailMapper;
    private final MixtureDetailService mixtureDetailService;

    @Override
    public long findNumber() {
        return this.mixtureRepository.count();
    }

    @Override
    public void create(Mixture mixture, Collection<MixtureDetailReq> mixtureDetailsReq) throws InsertException {
        try {
            Mixture mixtureSave = this.mixtureRepository.save(mixture);
            Collection<MixtureDetail> mixtureDetails =
                    this.mixtureDetailMapper.mixtureDetailResCollectionToMixtureDetailCollection(mixtureDetailsReq);
            mixtureDetails.forEach(mixtureDetail -> {mixtureDetail.setMixture(mixtureSave);});
            this.mixtureDetailService.createAll(mixtureDetails);
        } catch (Exception e) {
            log.error("Error to create mixture: {}", e.getMessage());
            throw new InsertException(TABLE_NAME, MESSAGE_INSERT);
        }
    }

    @Override
    public Collection<MixtureShortRes> findByQuery(String query) {
        return this.mixtureMapper.mixtureCollectionToMixtureShort(
                this.mixtureRepository.findAllValidMixtureFromQuery(query));
    }

    @Override
    public MixtureRes findByNumber(Integer number) {
        MixtureRes mixture = this.mixtureMapper.mixtureToMixtureRes(
                this.mixtureRepository.findValidMixtureByNumber(number)
        );
        mixture.setRows(this.mixtureDetailService.findAllByMixtureCode(mixture.getId()));
        return mixture;
    }

    @Override
    public Integer findNumberByLot(String lot) {
        try {
            Mixture mixture = this.mixtureRepository.findValidMixtureByLot(lot);
            return mixture.getNumber();
        }catch (Exception e){
            return 0;
        }
    }

    @Override
    public void edit(Integer id, Mixture mixture, Collection<MixtureDetailReq> mixtureDetailsReq) {
        Optional<Mixture> findMixture = mixtureRepository.findById(id);
        if(findMixture.isPresent()){
            mixture.setId(findMixture.get().getId());
            this.mixtureRepository.save(mixture);

            Collection<MixtureDetail> mixtureDetails =
                    this.mixtureDetailMapper.mixtureDetailResCollectionToMixtureDetailCollection(mixtureDetailsReq);
            mixtureDetails.forEach(mixtureDetail -> {mixtureDetail.setMixture(findMixture.get());});
            this.mixtureDetailService.edit(mixtureDetails);
        }else {
            throw new NotExistException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public GenerateMixtureReceiptReq generateReceiptData(Integer mixtureId) {
        Mixture mixture = this.mixtureRepository
                .findById(mixtureId)
                .orElseThrow(() ->
                        new BusinessException(BusinessExceptionReason.MIXTURE_NOT_FOUND, mixtureId));

        List<GenerateMixtureReceiptItemReq> items = new ArrayList<>();
        items.add(new GenerateMixtureReceiptItemReq(
                "HPO",
                "Braskem PH0130 (X.P)",
                "L0011",
                27.5,
                55.0,
                4455.00
        ));
        return GenerateMixtureReceiptReq.builder()
                .mixtureCode("PME2012-QL1W4-SI-110321-PROM. CHINA")
                .number("2012")
                .lot("L0014122")
                .clientProd("AGRO")
//                .isToExport(mixture.getOrder().getProject().getProjectType().getUniqueCode().equals(DesignConstant.EXPORT_CODE) ? "SI" : "NO")
                .isToExport("SI")
                .dieProduct("F144")
                .die("T001B(1x)-N/A-Corte Manual MASTER BATCH MASTER BATCH")
                .cyrel(null)
                .length(640)
                .width(1045)
                .documentBy("F. Salvador")
                .date(LocalDateTime.now())
                .prepare(81)
                .totalKg(16200)
                .leafs(78000)
                .preMixtureKg(mixture.getPreMixture())
                .mixture("QL1W4")
                .weight(300)
                .totalPercentage(BigDecimal.valueOf(100.00).setScale(2, RoundingMode.HALF_UP))
                .totalStopQuantity(BigDecimal.valueOf(200.14).setScale(2, RoundingMode.HALF_UP))
                .total(BigDecimal.valueOf(16200.35).setScale(2, RoundingMode.HALF_UP))
                .items(items)
                .build();
    }

    @Override
    public byte[] generateReceipt(GenerateMixtureReceiptReq receiptReq) {
        try {
            JasperReport jasperReport = receiptGeneratorUtil.getReportFromResources(ReceiptConstant.MIXTURE_JRXML_NAME);
            final Map<String, Object> parameters = receiptGeneratorUtil.getReportCommonData();
            parameters.put("mixtureCode", receiptReq.getMixtureCode());
            parameters.put("number", receiptReq.getNumber());
            parameters.put("lot", receiptReq.getLot());
            parameters.put("clientProd", receiptReq.getClientProd());
            parameters.put("isToExport", receiptReq.getIsToExport());
            parameters.put("dieProduct", receiptReq.getDieProduct());
            parameters.put("die", receiptReq.getDie());
            parameters.put("cyrel", receiptReq.getCyrel());
            parameters.put("length", receiptReq.getLength());
            parameters.put("width", receiptReq.getWidth());
            parameters.put("documentBy", receiptReq.getDocumentBy());
            parameters.put("date", DateUtil.generateReceiptDateTime(receiptReq.getDate()));
            parameters.put("prepare", receiptReq.getPrepare());
            parameters.put("totalKg", receiptReq.getTotalKg());
            parameters.put("leafs", receiptReq.getLeafs());
            parameters.put("preMixtureKg", receiptReq.getPreMixtureKg());
            parameters.put("mixture", receiptReq.getMixture());
            parameters.put("weight", receiptReq.getWeight());
            parameters.put("totalPercentage", receiptReq.getTotalPercentage());
            parameters.put("totalStopQuantity", receiptReq.getTotalStopQuantity());
            parameters.put("total", receiptReq.getTotal());
            parameters.put("itemsDataSource", new JRBeanCollectionDataSource(receiptReq.getItems()));
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            if (jasperPrint == null) {
                throw new BusinessException(BusinessExceptionReason.MIXTURE_RECEIPT_FAILED);
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            log.error("Error to generate receipt: {}", e.getMessage());
            throw new BusinessException(BusinessExceptionReason.MIXTURE_RECEIPT_FAILED);
        }
    }
}
