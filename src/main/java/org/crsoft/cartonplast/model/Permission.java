package org.crsoft.cartonplast.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * @author jyepez on 18/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTPER", schema = "carton_plast_test")
public class Permission {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CBTPER_CODE")
    private Integer id;

    @Column(name = "CBTPER_ROLE")
    private String role;

    @Column(name = "CBTPER_CREATE")
    private Boolean create;

    @Column(name = "CBTPER_READ")
    private Boolean read;

    @Column(name = "CBTPER_UPDATE")
    private Boolean update;

    @Column(name = "CBTPER_DELETE")
    private Boolean delete;

    @Column(
            name = "CBTPER_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTPER_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTPER_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTPER_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTPER_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTPER_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "CBTMEN_CODE",
            referencedColumnName = "CBTMEN_CODE",
            updatable = false
    )
    private Menu menu;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
