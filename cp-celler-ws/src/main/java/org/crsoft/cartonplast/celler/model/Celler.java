package org.crsoft.cartonplast.celler.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author jyepez on 31/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CDTCELL")
public class Celler {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CDTCELL_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(name = "CDTCELL_NUM_DOC", nullable = false)
    private String numberDocument;

    @Column(name = "CDTCELL_DATE", nullable = false)
    private LocalDateTime date;

    @Column(name = "CDTCELL_DATE_DOCUMENT", nullable = false)
    private LocalDateTime dateDocument;

    @Column(name = "CDTCELL_REASON")
    private String reason;

    @Column(name = "CDTCELL_OBSERVATION")
    private String observation;

    @Column(name = "CDTCELL_OBSERVATIONS")
    private String observations;

    @Column(name = "CDTCELL_ORIGIN")
    private String origin;

    @Column(name = "CDTCELL_DESTINY")
    private String destiny;

    @Column(name = "CDTCELL_STATE")
    private Boolean state;

    @Column(
            name = "CDTCELL_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CDTCELL_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CDTCELL_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CDTCELL_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CDTCELL_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CDTCELL_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @PrePersist
    public void prePersist() {
        this.state = Boolean.TRUE;
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }

}
