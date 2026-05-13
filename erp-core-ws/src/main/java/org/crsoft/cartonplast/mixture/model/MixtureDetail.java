package org.crsoft.cartonplast.mixture.model;

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
 * @author jyepez on 5/7/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CETMIX_DET")
@EntityListeners(AuditingEntityListener.class)
public class MixtureDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CETMIX_DET_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CETMIX_DET_PERCENT", nullable = false)
    private Double percent;

    @Column(name = "CETMIX_DET_STOP", nullable = false)
    private Double stop;

    @Column(name = "CETMIX_DET_TOTAL", nullable = false)
    private Double total;

    @Column(
            name = "CETMIX_DET_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CETMIX_DET_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CETMIX_DET_CREATED_BY", length = 16)
    @CreatedBy
    private String createdBy;

    @Column(name = "CETMIX_DET_UPDATED_BY", length = 16)
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CETMIX_DET_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CETMIX_DET_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CETMIX_CODE",
            referencedColumnName = "ID_CETMIX_CODE"
    )
    private Mixture mixture;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CDTCAT_CODE",
            referencedColumnName = "ID_CDTCAT_CODE"
    )
    private Material material;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
