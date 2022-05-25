package org.crsoft.cartonplast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author jyepez on 20/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTPER_CAT", schema = "carton_plast_test")
public class CatalogPermission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CBTPER_CAT_CODE")
    private Integer id;


    @Column(name = "CBTPER_CAT_NAME")
    private String name;

    @Column(
            name = "CBTPER_CAT_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTPER_CAT_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTPER_CAT_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTPER_CAT_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTPER_CAT_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTPER_CAT_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
