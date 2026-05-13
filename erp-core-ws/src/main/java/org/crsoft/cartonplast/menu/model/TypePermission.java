package org.crsoft.cartonplast.menu.model;

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
@Table(name = "CBTPER_TYPE", schema = "carton_plast_test")
public class TypePermission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CBTPER_TYPE")
    private Integer id;


    @Column(name = "CBTPER_TYPE_FLAG")
    private Boolean flag;

    @Column(
            name = "CBTPER_TYPE_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTPER_TYPE_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTPER_TYPE_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTPER_TYPE_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTPER_TYPE_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTPER_TYPE_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTPER_CAT_CODE",
            referencedColumnName = "ID_CBTPER_CAT_CODE",
            updatable = false
    )
    private CatalogPermission catalog;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTPER_CODE",
            referencedColumnName = "ID_CBTPER_CODE",
            updatable = false
    )
    private Permission permission;

    @PrePersist
    public void prePersist() {
        this.flag = Boolean.FALSE;
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
