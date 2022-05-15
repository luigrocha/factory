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
        name = "CATCIR",
        indexes = {
                @Index(name = "CAICIR_PRINT", columnList = "CATCIR_PRINT"),
        }
)
public class Cyrel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATCIR_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATCIR_PRINT",
            nullable = false,
            length = 8
    )
    private String print;

    @Column(
            name = "CATCIR_DESCRIPTION",
            nullable = false,
            length = 128
    )
    private String description;

    @Column(
            name = "CATCIR_DESCRIPTION2",
            length = 128
    )
    private String description2;

    @Column(
            name = "CATCIR_OBSERVATION",
            length = 128
    )
    private String observation;

    @Column(
            name = "CATCIR_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATCIR_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCIR_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCIR_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCIR_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATCIR_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATTRO_CODE",
            referencedColumnName = "ID_CATTRO_CODE",
            insertable = false,
            updatable = false
    )
    private Die die;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATIMP_CODE",
            referencedColumnName = "ID_CATIMP_CODE",
            insertable = false,
            updatable = false,
            nullable = false
    )
    private Printer printer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCOL_CODE",
            referencedColumnName = "ID_CATCOL_CODE",
            insertable = false,
            updatable = false
    )
    private ColorB mbLeaf;

    @OneToMany(
            mappedBy = "cyrel",
            fetch = FetchType.LAZY
    )
    private List<CyrelColor> cyrelColors = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
