package org.crsoft.cartonplast.vo.req;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lpillaga on 17/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class GenerateMaterialRequestReceiptReq implements Serializable {
    private static final long serialVersionUID = 1L;

    private String number;
    private String responsible;
    private LocalDateTime date;
    private String turn;
    private List<GenerateMaterialRequestReceiptItemReq> items;
}
