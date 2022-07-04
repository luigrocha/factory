package org.crsoft.cartonplast.mixture.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

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

    @Column(name = "CETMIX_NUMBER", nullable = false)
    private Integer number;

    @Column(name = "CETMIX_DOCUMENT_TO", nullable = false)
    private String documentTo;

    @Column(name = "CETMIX_DOCUMENT_BY", nullable = false)
    private String documentBy;

    @Column(name = "CETMIX_DATE", nullable = false)
    private Timestamp date;

    @Column(name = "CETMIX_PREPARE", nullable = false)
    private Integer prepare;

    @Column(name = "CETMIX_TOTAL", nullable = false)
    private Double total;

    @Column(name = "CETMIX_OBSERVATION")
    private String observation;

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
    private String createdBy;

    @Column(name = "CETMIX_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CETMIX_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CETMIX_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
