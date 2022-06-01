package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.crsoft.cartonplast.users.model.pk.PersonJobPositionId;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTPERSON_JOBPO")
public class PersonJobPosition {

    @EmbeddedId
    private PersonJobPositionId personJobPositionId;

    @Column(
            name = "CBTPERSON_JOBPO_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTPERSON_JOBPO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTPERSON_JOBPO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTPERSON_JOBPO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTPERSON_JOBPO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTPERSON_JOBPO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTPERSON_CODE",
            referencedColumnName = "ID_CBTPERSON_CODE",
            nullable = false
    )
    @MapsId("personId")
    private Person person;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTJOBPO_CODE",
            referencedColumnName = "ID_CBTJOBPO_CODE",
            nullable = false
    )
    @MapsId("jobPositionId")
    private JobPosition die;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
