package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 10/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "CATHOM",
        uniqueConstraints = {
                @UniqueConstraint(name = "CAIHOM_HP_CODE", columnNames = "CATHOM_HP_CODE")
        }
)
public class HomoPolymer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATHOM_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATHOM_PERCENT",
            nullable = false
    )
    private Integer percentage;

    @Column(
            name = "CATHOM_HP_CODE",
            nullable = false,
            length = 2
    )
    private String hpCode;

    @Column(
            name = "CATHOM_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATHOM_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATHOM_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATHOM_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATHOM_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATHOM_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
