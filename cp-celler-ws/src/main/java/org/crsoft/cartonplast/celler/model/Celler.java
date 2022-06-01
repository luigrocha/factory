package org.crsoft.cartonplast.celler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 31/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CDTCELL")
public class Celler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CDTCELL_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CDTCELL_LOTE", nullable = false)
    private String lote;

    @Column(name = "CDTCELL_AMOUNT")
    private Integer amount;

    @Column(name = "CDTCELL_BALANCE")
    private Integer balance;

    @Column(name = "CDTCELL_COAT")
    private Integer coat;

    @Column(name = "CDTCELL_PALLETS")
    private Integer pallets;

    @Column(name = "CDTCELL_WEIGHT")
    private Integer weight;

    @Column(name = "CDTCELL_DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "CDTCELL_OBSERVATION", nullable = false)
    private String observation;

    @Column(
            name = "CDTCELL_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CDTCELL_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CDTCELL_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CDTCELL_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CDTCELL_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CDTCELL_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

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
