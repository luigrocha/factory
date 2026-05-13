package org.crsoft.cartonplast.materialrequest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.celler.model.Material;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 15/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CFTREQMAT_DET")
public class MaterialRequestDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CFTREQMAT_DET_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CFTREQMAT_DET_BALANCE")
    private Double balance;

    @Column(name = "CFTREQMAT_DET_COAT")
    private Double coat;

    @Column(name = "CFTREQMAT_DET_PALLETS")
    private Double pallets;

    @Column(
            name = "CFTREQMAT_DET_WEIGHT",
            nullable = false
    )
    private Double weight;

    @Column(
            name = "CFTREQMAT_DET_CONSOLIDATED",
            nullable = false
    )
    private Double consolidated;

    @Column(
            name = "CFTREQMAT_DET_COAT_NUMBER",
            nullable = false
    )
    private Integer coatNumber;

    @Column(
            name = "CFTREQMAT_DET_PALLET_NUMBER",
            nullable = false
    )
    private Integer palletNumber;

    @Column(
            name = "CFTREQMAT_DET_OBSERVATION",
            length = 256
    )
    private String observation;

    @Column(
            name = "CFTREQMAT_DET_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CFTREQMAT_DET_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(
            name = "CFTREQMAT_DET_CREATED_BY",
            length = 16
    )
    @CreatedBy
    private String createdBy;

    @Column(
            name = "CFTREQMAT_DET_UPDATED_BY",
            length = 16
    )
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CFTREQMAT_DET_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CFTREQMAT_DET_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CFTREQMAT_CODE",
            referencedColumnName = "ID_CFTREQMAT_CODE",
            nullable = false
    )
    private MaterialRequest materialRequest;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CDTCAT_CODE",
            referencedColumnName = "ID_CDTCAT_CODE",
            nullable = false
    )
    private Material material;
}
