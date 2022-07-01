package org.crsoft.cartonplast.celler.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.sf.jasperreports.engine.*;
import org.crsoft.cartonplast.celler.service.IReceiptService;
import org.crsoft.cartonplast.celler.util.ReceiptGeneratorUtil;
import org.crsoft.cartonplast.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.constant.ReceiptConstant;
import org.crsoft.cartonplast.common.enums.ReceiptType;
import org.crsoft.cartonplast.common.exception.ReceiptGeneratorException;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * @author lpillaga on 05/06/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class ReceiptCEBService implements IReceiptService {

    private final ReceiptGeneratorUtil receiptGeneratorUtil;

    @Override
    public String getReceiptType() {
        return ReceiptType.CEB.getType();
    }

    @Override
    public byte[] generateReceipt(GenerateReceiptReq generateReceiptReq) {
        try {
            JasperReport jasperReport = receiptGeneratorUtil.getReportFromResources(ReceiptConstant.CEB_JRXML_NAME);

            final Map<String, Object> parameters = receiptGeneratorUtil.getReportCommonData(generateReceiptReq);

            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, new JREmptyDataSource());

            if (jasperPrint == null) {
                throw new ReceiptGeneratorException("Error al generar el reporte CEB");
            }

            return JasperExportManager.exportReportToPdf(jasperPrint);
        } catch (JRException e) {
            log.error("Error generating receipt", e);
            throw new ReceiptGeneratorException("No se pudo generar el reporte CEB", e);
        }
    }
}
