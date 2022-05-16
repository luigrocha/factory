package org.crsoft.cartonplast.common.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 13/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATSTATUS")
public class CatalogStatus {

    @Id
    @Column(
            name = "ID_CATSTATUS_CODE",
            nullable = false,
            updatable = false,
            length = 4
    )
    private String id;

    @Column(
            name = "CATSTATUS_NAME",
            nullable = false,
            length = 16
    )
    private String name;

    @Column(
            name = "CATSTATUS_COLOR",
            length = 8
    )
    private String color;

    @Column(
            name = "CATSTATUS_BACKGROUND_COLOR",
            length = 8
    )
    private String backgroundColor;

    @Column(
            name = "CATSTATUS_TYPE",
            length = 2,
            nullable = false
    )
    private String type;

    @Column(
            name = "CATSTATUS_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATSTATUS_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATSTATUS_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATSTATUS_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATSTATUS_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATSTATUS_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
