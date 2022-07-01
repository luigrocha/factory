package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 12/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "CATPRODTRO",
        indexes = {
                @Index(name = "CAIPRODTRO_PTROQ", columnList = "CATPRODTRO_PTROQ")
        }
)
public class DieProduct {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATPRODTRO_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATPRODTRO_PTROQ",
            nullable = false,
            length = 8
    )
    private String code;

    @Column(
            name = "CATPRODTRO_NAME",
            nullable = false,
            length = 128
    )
    private String name;

    @Column(name = "CATPRODTRO_AREA")
    private Double area;

    @Column(name = "CATPRODTRO_LENGTH")
    private Double length;

    @Column(name = "CATPRODTRO_WIDTH")
    private Double width;

    @Column(name = "CATPRODTRO_GSMDIS")
    private Double gsmdis;

    @Column(
            name = "CATPRODTRO_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATPRODTRO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATPRODTRO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATPRODTRO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATPRODTRO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATPRODTRO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATSTATUS_CODE",
            nullable = false
    )
    private CatalogStatus status;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "dieProduct"
    )
    private List<Die> dies = new ArrayList<>();

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "dieProduct"
    )
    private List<CyrelDieProduct> cyrels = new ArrayList<>();
}
