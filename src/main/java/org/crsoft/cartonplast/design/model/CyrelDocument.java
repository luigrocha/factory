package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 20/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATCIRDOC")
public class CyrelDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATCIRDOC_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATCIRDOC_VERSION",
            nullable = false
    )
    private Integer version;

    @Column(
            name = "CATCIRDOC_NAME",
            nullable = false,
            length = 256
    )
    private String name;

    @Column(
            name = "CATCIRDOC_VERSION_DATE",
            columnDefinition = "TIMESTAMP",
            nullable = false
    )
    private LocalDateTime versionDate;

    @Column(
            name = "CATCIRDOC_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATCIRDOC_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCIRDOC_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCIRDOC_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCIRDOC_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATCIRDOC_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCIR_CODE",
            referencedColumnName = "ID_CATCIR_CODE",
            nullable = false
    )
    private Cyrel cyrel;

    @PrePersist
    public void prePersist() {
        this.versionDate = LocalDateTime.now();
    }
}
