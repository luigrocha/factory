package org.crsoft.cartonplast.celler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 31/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CDTCELL_DET")
public class CellerDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CDTCELL_DET_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CDTCELL_DET_LOTE", nullable = false)
    private String lote;

    @Column(name = "CDTCELL_DET_AMOUNT")
    private Integer amount;

    @Column(name = "CDTCELL_DET_BALANCE")
    private Double balance;

    @Column(name = "CDTCELL_DET_COAT")
    private Double coat;

    @Column(name = "CDTCELL_DET_PALLETS")
    private Double pallets;

    @Column(name = "CDTCELL_DET_WEIGHT")
    private Double weight;

    @Column(
            name = "CDTCELL_DET_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CDTCELL_DET_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CDTCELL_DET_CREATED_BY", length = 16)
    @CreatedBy
    private String createdBy;

    @Column(name = "CDTCELL_DET_UPDATED_BY", length = 16)
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CDTCELL_DET_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CDTCELL_DET_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CDTCELL_CODE",
            referencedColumnName = "ID_CDTCELL_CODE"
    )
    private Celler celler;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CDTCAT_ID",
            referencedColumnName = "ID_CDTCAT_CODE"
    )
    private Material material;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CDTLOC_CODE",
            referencedColumnName = "ID_CDTLOC_CODE"
    )
    private Location location;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CDTDOC_CODE",
            referencedColumnName = "ID_CDTDOC_CODE"
    )
    private Document document;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

}
