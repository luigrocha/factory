package org.crsoft.cartonplast.celler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 1/6/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CDTOPT")
public class OptionDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CDTOPT_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CDTOPT_NAME", nullable = false)
    private String name;

    @Column(name = "CDTOPT_DESCRIPTION", nullable = false)
    private String description;

    @Column(
            name = "CDTOPT_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CDTOPT_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CDTOPT_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CDTOPT_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CDTOPT_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CDTOPT_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

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
