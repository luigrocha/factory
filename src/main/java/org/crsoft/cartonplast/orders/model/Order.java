package org.crsoft.cartonplast.orders.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.catalog.model.CatalogPriority;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.design.model.Project;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * @author jyepez on 14/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "COTORD",
        uniqueConstraints = {
                @UniqueConstraint(name = "COIORD_CODE", columnNames = "COTORD_CODE")
        }
)
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_COTORD_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "COTORD_CODE",
            nullable = false,
            length = 8
    )
    private String code;

    @Column(
            name = "COTORD_PRODUCT_CODE",
            nullable = false,
            length = 128
    )
    private String productCode;

    @Column(
            name = "COTORD_NAME",
            nullable = false,
            length = 128
    )
    private String name;

    @Column(
            name = "COTORD_QUANTITY",
            nullable = false
    )
    private Integer quantity;

    @Column(
            name = "COTORD_ORDERED_AT",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime orderedAt;

    @Column(
            name = "COTORD_LOTE",
            length = 16
    )
    private String lot;

    @Column(
            name = "COTORD_ESTIMATED_DELIVERED_AT",
            nullable = false
    )
    private LocalDate estimatedDeliveryAt;

    @Column(
            name = "COTORD_CLIENT_ORDER_CODE",
            length = 64
    )
    private String clientOrderCode;

    @Column(
            name = "COTORD_OBSERVATION",
            length = 256
    )
    private String observation;

    @Column(
            name = "COTORD_COMPLETED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime completedAt;

    @Column(
            name = "COTORD_CANCELED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime canceledAt;

    @Column(
            name = "COTORD_PRODUCTION_STARTED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime productionStartedAt;

    @Column(
            name = "COTORD_CANCELLATION_REASON",
            length = 256
    )
    private String cancellationReason;

    @Column(
            name = "COTORD_LAST_MODIFICATION_AT",
            columnDefinition = "TIMESTAMP",
            nullable = false
    )
    @LastModifiedDate
    private LocalDateTime lastModifiedAt;

    @Column(
            name = "COTORD_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "COTORD_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(
            name = "COTORD_CREATED_BY",
            length = 16
    )
    @CreatedBy
    private String createdBy;

    @Column(
            name = "COTORD_UPDATED_BY",
            length = 16
    )
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "COTORD_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "COTORD_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCLI_CODE",
            referencedColumnName = "ID_CATCLI_CODE",
            nullable = false
    )
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATSTATUS_CODE",
            referencedColumnName = "ID_CATSTATUS_CODE",
            nullable = false
    )
    private CatalogStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATPRI_CODE",
            referencedColumnName = "ID_CATPRI_CODE",
            nullable = false
    )
    private CatalogPriority priority;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATPROY_CODE",
            referencedColumnName = "ID_CATPROY_CODE",
            nullable = false
    )
    private Project project;

    @PrePersist
    public void prePersist() {
        this.orderedAt = LocalDateTime.now();
    }
}
