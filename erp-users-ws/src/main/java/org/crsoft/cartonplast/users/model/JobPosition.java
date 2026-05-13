package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTJOBPO")
public class JobPosition {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CBTJOBPO_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CBTJOBPO_NAME",
            length = 128,
            nullable = false
    )
    private String name;

    @Column(
            name = "CBTJOBPO_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTJOBPO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTJOBPO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTJOBPO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTJOBPO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTJOBPO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
