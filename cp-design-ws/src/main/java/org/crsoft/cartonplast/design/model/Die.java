package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.common.model.CatalogStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATTRO")
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
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATTRO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATTRO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATTRO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATTRO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATTRO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATFAB_CODE",
            referencedColumnName = "ID_CATFAB_CODE"
    )
    private Manufacturer manufacturer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATSTATUS_CODE",
            nullable = false
    )
    private CatalogStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATPRODTRO_CODE",
            referencedColumnName = "ID_CATPRODTRO_CODE",
            nullable = false
    )
    private DieProduct dieProduct;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "die",
            cascade = CascadeType.ALL
    )
    private List<DieMachine> dieMachines = new ArrayList<>();

    @OneToMany(
            mappedBy = "die",
            fetch = FetchType.LAZY
    )
    private List<DieDocument> documents = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        createdDate = LocalDate.now();
    }
}
