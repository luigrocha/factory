package org.crsoft.cartonplast.celler.model;

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
@Table(name = "CDTTIP")
public class TypeMaterial {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CDTTIP_ID",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CDTTIP_NAME", nullable = false)
    private String name;

    @Column(
            name = "CDTTIP_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CDTTIP_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CDTTIP_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CDTTIP_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CDTTIP_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CDTTIP_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
