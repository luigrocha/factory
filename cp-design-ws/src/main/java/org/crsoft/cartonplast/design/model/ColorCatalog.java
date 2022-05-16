package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 13/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATCATCOL")
public class ColorCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATCATCOL_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATCATCOL_NAME",
            nullable = false,
            length = 256
    )
    private String name;

    @Column(
            name = "CATCATCOL_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATCATCOL_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCATCOL_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCATCOL_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCATCOL_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATCATCOL_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "color",
            fetch = FetchType.LAZY
    )
    private List<CyrelColor> cyrelColors = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
