package org.crsoft.cartonplast.order.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.common.model.CatalogPriority;
import org.crsoft.cartonplast.common.model.CatalogStatus;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 14/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CCTORD")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CCTORD_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CCTORD_LOTE")
    private String lote;

    @Column(
            name = "CCTORD_AMOUNT",
            nullable = false
    )
    private Integer amount;

    @Column(
            name = "CCTORD_DELIVER_AT",
            nullable = false
    )
    private LocalDateTime deliverAt;

    @Column(
            name = "CCTORD_ORDER"
    )
    private Integer order;

    @Column(name = "CCTORD_OBSERVATION")
    private String observation;

    @Column(name = "CCTORD_DIFERENCE")
    private Integer difference;

    @Column(
            name = "CCTORD_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CCTORD_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CCTORD_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CCTORD_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CCTORD_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CCTORD_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCLI_CODE",
            referencedColumnName = "ID_CATCLI_CODE",
            insertable = false,
            updatable = false
    )
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATSTATUS_CODE",
            referencedColumnName = "ID_CATSTATUS_CODE",
            insertable = false,
            updatable = false
    )
    private CatalogStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATPRI_CODE",
            referencedColumnName = "ID_CATPRI_CODE",
            insertable = false,
            updatable = false
    )
    private CatalogPriority priority;
}
