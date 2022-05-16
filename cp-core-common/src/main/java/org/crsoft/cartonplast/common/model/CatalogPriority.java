package org.crsoft.cartonplast.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 14/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATPRI")
public class CatalogPriority {

    @Id
    @Column(
            name = "ID_CATPRI_CODE",
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(name = "CATPRI_NAME" ,nullable = false)
    private String name;

    @Column(name = "CATPRI_TYPE" ,nullable = false)
    private String type;

    @Column(name = "CATPRI_ICON" ,nullable = false)
    private String icon;

    @Column(name = "CATPRI_COLOR")
    private String color;

    @Column(
            name = "CATPRI_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATPRI_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATPRI_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATPRI_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATPRI_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATPRI_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
