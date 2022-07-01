package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATCOL")
public class ColorB {

    @Id
    @Column(
            name = "ID_CATCOL_CODE",
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CATCOL_INDEX",
            nullable = false
    )
    private Integer index;

    @Column(
            name = "CATCOL_DOSAGE",
            nullable = false
    )
    private Double dosage;

    @Column(
            name = "CATCOL_DESCRIPTION",
            nullable = false
    )
    private String description;

    @Column(
            name = "CATCOL_COLOR_CODE",
            nullable = false
    )
    private String colorCode;

    @Column(
            name = "CATCOL_OBSERVATION",
            length = 255
    )
    private String observation;

    @Column(
            name = "CATCOL_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATCOL_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCOL_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCOL_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCOL_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATCOL_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCOP_CODE",
            referencedColumnName = "ID_CATCOP_CODE"
    )
    private ColorA colorA;

    @OneToMany(
            mappedBy = "mbLeaf",
            fetch = FetchType.LAZY
    )
    private List<Cyrel> cyrels = new ArrayList<>();
}
