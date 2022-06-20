package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 13/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATCIR_COL")
public class CyrelColor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATCIR_COL_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATCIR_COL_INDEX",
            nullable = false
    )
    private Integer index;

    @Column(
            name = "CATCIR_COL_OBSERVATION",
            length = 256
    )
    private String observation;

    @Column(
            name = "CATCIR_COL_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATCIR_COL_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCIR_COL_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCIR_COL_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCIR_COL_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATCIR_COL_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCIR_CODE",
            referencedColumnName = "ID_CATCIR_CODE",
            nullable = false
    )
    private Cyrel cyrel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCATCOL_CODE",
            referencedColumnName = "ID_CATCATCOL_CODE",
            nullable = false
    )
    private ColorCatalog color;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCOLTI_CODE",
            referencedColumnName = "ID_CATCOLTI_CODE",
            nullable = false
    )
    private ColorType colorType;
}
