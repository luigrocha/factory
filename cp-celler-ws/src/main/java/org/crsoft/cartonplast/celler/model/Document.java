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
@Table(name = "CDTDOC")
public class Document {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CDTDOC_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CDTDOC_NAME", nullable = false)
    private String name;

    @Column(name = "CDTDOC_DESCRIPTION", nullable = false)
    private String description;

    @Column(
            name = "CDTDOC_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CDTDOC_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CDTDOC_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CDTDOC_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CDTDOC_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CDTDOC_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
