package org.crsoft.cartonplast.celler.util;

import lombok.RequiredArgsConstructor;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.constant.ReceiptConstant;
import org.crsoft.cartonplast.common.exception.ReceiptGeneratorException;
import org.crsoft.cartonplast.common.util.DateUtil;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lpillaga on 05/06/2022
 */
@Component
@RequiredArgsConstructor
public class ReceiptGeneratorUtil {

    private final ResourceLoader resourceLoader;

    public JasperReport getReportFromResources(String fileName) {
        try {
            Resource resource = resourceLoader.getResource(ReceiptConstant.RECEIPT_RESOURCES_PATH + fileName);
            InputStream stream = resource.getInputStream();
            return JasperCompileManager.compileReport(stream);
        } catch (JRException | IOException e) {
            throw new ReceiptGeneratorException(e.getMessage());
        }
    }

    public Map<String, Object> getStoreReportCommonData(GenerateReceiptReq generateReceiptReq) {
        final Map<String, Object> parameters = new HashMap<>();
        try {
            Resource resource = resourceLoader.getResource(GlobalConstant.LOGO_CP_PATH);

            parameters.put("cpLogo", resource.getInputStream());
            parameters.put("receiptNumber", generateReceiptReq.getReceiptNumber());
            parameters.put("documentDate", DateUtil.generateReceiptDateTime(generateReceiptReq.getReceiptDate()));
            parameters.put("reason", generateReceiptReq.getReason());
            parameters.put("reasonObservation", generateReceiptReq.getReasonObservation());
            parameters.put("observations", generateReceiptReq.getObservations());
            parameters.put("deliveredBy", generateReceiptReq.getDeliveredBy());
            parameters.put("receivedBy", generateReceiptReq.getReceivedBy());
            parameters.put("itemsDataSource", new JRBeanCollectionDataSource(generateReceiptReq.getItems()));
        } catch (IOException e) {
            throw new ReceiptGeneratorException("No se pudo encontrar el logo de Carton Plast");
        }
        return parameters;
    }

    public Map<String, Object> getReportCommonData() {
        final Map<String, Object> parameters = new HashMap<>();
        try {
            Resource resource = resourceLoader.getResource(GlobalConstant.LOGO_CP_PATH);
            parameters.put("cpLogo", resource.getInputStream());
        } catch (IOException e) {
            throw new BusinessException(BusinessExceptionReason.MIXTURE_RECEIPT_FAILED);
        }
        return parameters;
    }
}
