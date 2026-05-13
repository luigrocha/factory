package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTREL")
public class Relationship {

    @Id
    @Column(
            name = "ID_CBTREL_CODE",
            length = 2,
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CBTREL_NAME",
            length = 32,
            nullable = false
    )
    private String name;

    @Column(
            name = "CBTREL_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTREL_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTREL_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTREL_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTREL_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTREL_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "relationship", fetch = FetchType.LAZY)
    private List<EmergencyContact> emergencyContacts = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
