package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.client.model.Client;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 23/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATPROY")
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATPROY_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATPROY_NAME",
            nullable = false,
            length = 128
    )
    private String name;

    @Column(
            name = "CATPROY_CODEGEN",
            nullable = false,
            length = 128
    )
    private String codeGen;

    @Column(
            name = "CATPROY_GSM",
            nullable = false
    )
    private Integer gsm;

    @Column(
            name = "CATPROY_AYD_PROCESS",
            nullable = false
    )
    private Boolean aydProcess;

    @Column(
            name = "CATPROY_IS_STRUCTURAL",
            nullable = false
    )
    private Boolean isStructural;

    @Column(
            name = "CATPROY_HAS_CROWN",
            nullable = false
    )
    private Boolean hasCrown;

    @Column(
            name = "CATPROY_LEAF_NAME",
            length = 64
    )
    private String leafName;

    @Column(
            name = "CATPROY_REFERENCE_TAG",
            length = 64
    )
    private String referenceTag;

    @Column(
            name = "CATPROY_HAS_LOGO",
            nullable = false
    )
    private Boolean hasLogo;

    @Column(
            name = "CATPROY_QUANTITY_X_PACKAGE"
    )
    private Integer quantityXPackage;

    @Column(
            name = "CATPROY_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATPROY_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATPROY_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATPROY_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATPROY_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATPROY_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCLI_CODE"
    )
    private Client client;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATHOM_CODE",
            nullable = false
    )
    private HomoPolymer homoPolymer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCOL_CODE"
    )
    private ColorB colorB;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCIR_CODE"
    )
    private Cyrel cyrel;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCTAL_CODE"
    )
    private Talc talc;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCCACO3_CODE"
    )
    private CalciumCarbonate calciumCarbonate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATPRODTRO_CODE"
    )
    private DieProduct dieProduct;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCTIPPRO_CODE"
    )
    private ProjectType projectType;
}
