package org.crsoft.cartonplast.design.model;

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
@Table(name = "CATTROSTA")
public class DieStatus {

    @Id
    @Column(
            name = "ID_CATROSTA_CODE",
            nullable = false,
            updatable = false,
            length = 2
    )
    private String id;

    @Column(
            name = "CATROSTA_NAME",
            nullable = false,
            length = 16
    )
    private String name;

    @Column(
            name = "CATROSTA_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATROSTA_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATROSTA_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATROSTA_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATROSTA_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATROSTA_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
