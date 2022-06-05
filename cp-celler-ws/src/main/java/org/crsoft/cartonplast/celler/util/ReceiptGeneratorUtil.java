package org.crsoft.cartonplast.celler.util;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import org.crsoft.cartonplast.celler.vo.req.GenerateReceiptReq;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.constant.ReceiptConstant;
import org.crsoft.cartonplast.common.exception.ReceiptGeneratorException;
import org.crsoft.cartonplast.common.util.DateUtil;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lpillaga on 05/06/2022
 */
@Component
public class ReceiptGeneratorUtil {

    public JasperReport getReportFromResources(String fileName) {
        try {
            File file = ResourceUtils.getFile(ReceiptConstant.RECEIPT_RESOURCES_PATH + fileName);
            return JasperCompileManager.compileReport(file.getAbsolutePath());
        }
        catch (FileNotFoundException | JRException e) {
            throw new ReceiptGeneratorException(e.getMessage());
        }
    }

    public Map<String, Object> getReportCommonData(GenerateReceiptReq generateReceiptReq) {
        final Map<String, Object> parameters = new HashMap<>();
        try {
            parameters.put("cpLogo", new FileInputStream(ResourceUtils.getFile(GlobalConstant.LOGO_CP_PATH)));
            parameters.put("receiptNumber", generateReceiptReq.getReceiptNumber());
            parameters.put("documentDate", DateUtil.generateReceiptDateTime(generateReceiptReq.getReceiptDate()));
            parameters.put("reason", generateReceiptReq.getReason());
            parameters.put("reasonObservation", generateReceiptReq.getReasonObservation());
            parameters.put("observations", generateReceiptReq.getObservations());
            parameters.put("deliveredBy", generateReceiptReq.getDeliveredBy());
            parameters.put("receivedBy", generateReceiptReq.getReceivedBy());
            parameters.put("itemsDataSource", new JRBeanCollectionDataSource(generateReceiptReq.getItems()));
        } catch (FileNotFoundException e) {
            throw new ReceiptGeneratorException("No se pudo encontrar el logo de Carton Plast");
        }
        return parameters;
    }

}
