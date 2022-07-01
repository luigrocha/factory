package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.design.model.pk.CyrelDieProductId;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 12/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATCIR_TRO")
public class CyrelDieProduct {

    @EmbeddedId
    private CyrelDieProductId cyrelDieProductId;

    @Column(
            name = "CATCIR_TRO_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATCIR_TRO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCIR_TRO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCIR_TRO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCIR_TRO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATCIR_TRO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATPRODTRO_CODE",
            referencedColumnName = "ID_CATPRODTRO_CODE",
            nullable = false
    )
    @MapsId("dieProductId")
    private DieProduct dieProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCIR_CODE",
            referencedColumnName = "ID_CATCIR_CODE",
            nullable = false
    )
    @MapsId("cyrelId")
    private Cyrel cyrel;
}
