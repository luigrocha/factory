package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "CATCOP",
        uniqueConstraints = {
                @UniqueConstraint(name = "CAICOP_NAME", columnNames = "CATCOP_NAME")
        }
)
public class ColorA {

    @Id
    @Column(
            name = "ID_CATCOP_CODE",
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CATCOP_NAME",
            nullable = false
    )
    private String name;

    @Column(
            name = "CATCOP_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATCOP_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCOP_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCOP_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCOP_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATCOP_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "colorA"
    )
    private List<ColorB> colorsB = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
