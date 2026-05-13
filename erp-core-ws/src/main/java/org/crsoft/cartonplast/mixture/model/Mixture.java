package org.crsoft.cartonplast.mixture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.design.model.Die;
import org.crsoft.cartonplast.orders.model.Order;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

/**
 * @author jyepez on 3/7/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CETMIX")
public class Mixture {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CETMIX_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CETMIX_PREPARE", nullable = false)
    private Integer prepare;

    @Column(name = "CETMIX_PRE_MIXTURE", nullable = false)
    private Integer preMixture;

    @Column(name = "CETMIX_OBSERVATION")
    private String observation;

    @Column(name = "CETMIX_NUMBER", nullable = false)
    private Integer number;

    @Column(name = "CETMIX_DOCUMENT_BY", nullable = false)
    private String documentBy;

    @Column(name = "CETMIX_DOCUMENT_TO", nullable = false)
    private String documentTo;

    @Column(name = "CETMIX_MIXTURE", nullable = false)
    private String mixture;

    @Column(name = "CETMIX_DATE", nullable = false)
    private Timestamp date;

    @Column(name = "CETMIX_TOTAL_STOP")
    private Double totalStop;

    @Column(name = "CETMIX_TOTAL", nullable = false)
    private Double total;

    @Column(name = "CETMIX_TOTAL_REAL", nullable = false)
    private Double totalReal;

    @Column(
            name = "CETMIX_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CETMIX_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CETMIX_CREATED_BY", length = 16)
    @CreatedBy
    private String createdBy;

    @Column(name = "CETMIX_UPDATED_BY", length = 16)
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CETMIX_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CETMIX_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_COTORD_CODE",
            referencedColumnName = "ID_COTORD_CODE"
    )
    private Order order;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATTRO_CODE",
            referencedColumnName = "ID_CATTRO_CODE"
    )
    private Die die;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
