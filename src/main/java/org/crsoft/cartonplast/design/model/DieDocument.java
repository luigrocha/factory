package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.design.model.Die;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 21/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATTRODOC")
public class DieDocument {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATTRODOC_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATTRODOC_VERSION",
            nullable = false
    )
    private Integer version;

    @Column(
            name = "CATTRODOC_NAME",
            nullable = false,
            length = 256
    )
    private String name;

    @Column(
            name = "CATTRODOC_VERSION_DATE",
            columnDefinition = "TIMESTAMP",
            nullable = false
    )
    private LocalDateTime versionDate;

    @Column(
            name = "CATTRODOC_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATTRODOC_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATTRODOC_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATTRODOC_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATTRODOC_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATTRODOC_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATTRO_CODE",
            referencedColumnName = "ID_CATTRO_CODE",
            nullable = false
    )
    private Die die;

    @PrePersist
    public void prePersist() {
        this.versionDate = LocalDateTime.now();
    }
}
