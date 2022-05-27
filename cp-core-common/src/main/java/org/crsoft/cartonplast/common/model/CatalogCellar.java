package org.crsoft.cartonplast.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 26/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CDTCAT")
public class CatalogCellar {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CDTCAT_ID",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CDTCAT_NAME", nullable = false)
    private String name;

    @Column(
            name = "CDTCAT_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CDTCAT_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CDTCAT_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CDTCAT_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CDTCAT_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CDTCAT_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CDTTIP_ID",
            referencedColumnName = "ID_CDTTIP_ID"
    )
    private TypeCellar typeCellar;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
