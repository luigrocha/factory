package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 11/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATTHI")
public class Thickness {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATTHI_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATTHI_WEIGHT",
            nullable = false
    )
    private Integer weight;

    @Column(name = "CATTHI_THICKNESS")
    private Double thick;

    @Column(
            name = "CATTHI_USD",
            nullable = false
    )
    private Double usd;

    @Column(
            name = "CATTHI_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATTHI_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATTHI_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATTHI_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATTHI_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATTHI_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
