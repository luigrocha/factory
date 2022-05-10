package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.design.model.pk.DieMachineId;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 05/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATMAQ_TRO")
public class DieMachine {

    @EmbeddedId
    private DieMachineId dieMachineId;

    @Column(
            name = "CATMAQ_TRO_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATMAQ_TRO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATMAQ_TRO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATMAQ_TRO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATMAQ_TRO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATMAQ_TRO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATMAQ_CODE",
            referencedColumnName = "ID_CATMAQ_CODE",
            nullable = false
    )
    @MapsId("machineId")
    private Machine machine;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATTRO_CODE",
            referencedColumnName = "ID_CATTRO_CODE",
            nullable = false
    )
    @MapsId("dieId")
    private Die die;
}
