package org.crsoft.cartonplast.materialrequest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CFTREQMAT")
public class MaterialRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CFTREQMAT_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CFTREQMAT_NUMBER",
            nullable = false,
            length = 32
    )
    private String number;

    @Column(
            name = "CFTREQMAT_DATE",
            nullable = false
    )
    private LocalDateTime date;

    @Column(
            name = "CFTREQMAT_DOC_BY_USERNAME",
            length = 16,
            nullable = false
    )
    private String documentByUsername;

    @Column(
            name = "CFTREQMAT_DOC_BY",
            length = 128,
            nullable = false
    )
    private String documentBy;

    @Column(
            name = "CFTREQMAT_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CFTREQMAT_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(
            name = "CFTREQMAT_CREATED_BY",
            length = 16
    )
    @CreatedBy
    private String createdBy;

    @Column(
            name = "CFTREQMAT_UPDATED_BY",
            length = 16
    )
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CFTREQMAT_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CFTREQMAT_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATSTATUS_CODE",
            referencedColumnName = "ID_CATSTATUS_CODE",
            nullable = false
    )
    private CatalogStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CFTTURN_CODE",
            referencedColumnName = "ID_CFTTURN_CODE",
            nullable = false
    )
    private Turn turn;

    @OneToMany(
            mappedBy = "materialRequest",
            fetch = FetchType.LAZY
    )
    private List<MaterialRequestDetail> items = new ArrayList<>();
}
