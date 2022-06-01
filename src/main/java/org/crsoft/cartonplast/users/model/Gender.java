package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 30/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTGEN")
public class Gender {

    @Id
    @Column(
            name = "ID_CBTGEN_CODE",
            length = 1,
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CBTGEN_NAME",
            length = 32,
            nullable = false
    )
    private String name;

    @Column(
            name = "CBTGEN_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTGEN_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTGEN_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTGEN_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTGEN_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTGEN_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
