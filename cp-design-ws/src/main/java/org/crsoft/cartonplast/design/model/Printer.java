package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "CATIMP",
        indexes = {
                @Index(name = "CAIIMP_NAME", columnList = "CATIMP_NAME"),
        }
)
public class Printer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATIMP_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATIMP_NAME",
            nullable = false,
            length = 64
    )
    private String name;

    @Column(
            name = "CATIMP_NUM_COLORS",
            nullable = false
    )
    private Integer numColors;

    @Column(
            name = "CATIMP_DESCRIPTION",
            length = 64
    )
    private String description;

    @Column(
            name = "CATIMP_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATIMP_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATIMP_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATIMP_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATIMP_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATIMP_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "printer",
            fetch = FetchType.LAZY
    )
    private List<Cyrel> cyrels = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
