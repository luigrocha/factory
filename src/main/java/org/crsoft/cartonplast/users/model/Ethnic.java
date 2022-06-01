package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTETH")
public class Ethnic {

    @Id
    @Column(
            name = "ID_CBTETH_CODE",
            length = 2,
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CBTETH_NAME",
            length = 32,
            nullable = false
    )
    private String name;

    @Column(
            name = "CBTETH_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTETH_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTETH_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTETH_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTETH_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTETH_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "ethnic", fetch = FetchType.LAZY)
    private List<Person> persons = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
