package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.common.model.CatalogStatus;


import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "CATTRO",
        indexes = {
                @Index(name = "CAITRO_PTROQ", columnList = "CATTRO_PTROQ")
        }
)
public class Die {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATTRO_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATTRO_PTROQ",
            nullable = false,
            length = 8
    )
    private String code;

    @Column(
            name = "CATTRO_NAME",
            nullable = false,
            length = 64
    )
    private String name;

    @Column(
            name = "CATTRO_CREATED_DATE",
            nullable = false,
            columnDefinition = "TIMESTAMP"
    )
    private LocalDate createdDate;

    @Column(
            name = "CATTRO_DESCRIPTION",
            nullable = false,
            length = 128
    )
    private String description;

    @Column(
            name = "CATTRO_AREA",
            nullable = false
    )
    private Double area;

    @Column(
            name = "CATTRO_LENGTH",
            nullable = false
    )
    private Double length;

    @Column(
            name = "CATTRO_WIDTH",
            nullable = false
    )
    private Double width;

    @Column(
            name = "CATTRO_GSMDIS",
            nullable = false
    )
    private Double gsmdis;

    @Column(
            name = "CATTRO_DSB_MULTIPLE",
            length = 32
    )
    private String dsbMultiple;

    @Column(
            name = "CATTRO_OBSERVATIONS",
            length = 128
    )
    private String observations;

    @Column(
            name = "CATTRO_QUANTITY",
            nullable = false
    )
    private Double quantity;

    @Column(
            name = "CATTRO_QUANTITY_L",
            nullable = false
    )
    private Double quantityLength;

    @Column(
            name = "CATTRO_SEPARATION_L",
            nullable = false
    )
    private Double separationLength;

    @Column(
            name = "CATTRO_QUANTITY_W",
            nullable = false
    )
    private Double quantityWidth;

    @Column(
            name = "CATTRO_SEPARATION_W",
            nullable = false
    )
    private Double separationWidth;

    @Column(
            name = "CATTRO_BORDER_L",
            nullable = false
    )
    private Double borderLength;

    @Column(
            name = "CATTRO_BORDER_W",
            nullable = false
    )
    private Double borderWidth;

    @Column(
            name = "CATTRO_LEAF_L",
            nullable = false
    )
    private Double leafLength;

    @Column(
            name = "CATTRO_LEAF_W",
            nullable = false
    )
    private Double leafWidth;

    @Column(
            name = "CATTRO_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATTRO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATTRO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATRO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATRO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATRO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATFAB_CODE",
            referencedColumnName = "ID_CATFAB_CODE",
            insertable = false,
            updatable = false
    )
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATSTATUS_CODE",
            referencedColumnName = "ID_CATSTATUS_CODE",
            insertable = false,
            updatable = false,
            nullable = false
    )
    private CatalogStatus status;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "die"
    )
    private List<DieMachine> dieMachines = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "die"
    )
    private List<Cyrel> cyrels = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
