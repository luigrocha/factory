package org.crsoft.cartonplast.celler.vo.req;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lpillaga on 04/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class GenerateReceiptReq implements Serializable {

    private static final long serialVersionUID = 1L;

    private String receiptNumber;
    private LocalDateTime receiptDate;
    private String reason;
    private String reasonObservation;
    private String observations;
    private String deliveredBy;
    private String receivedBy;
    private List<GenerateReceiptItemReq> items;
}
