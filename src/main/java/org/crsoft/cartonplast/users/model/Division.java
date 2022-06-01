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
@Table(name = "CBTDIV")
public class Division {

    @Id
    @Column(
            name = "ID_CBTDIV_CODE",
            length = 1,
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CBTDIV_NAME",
            length = 32,
            nullable = false
    )
    private String name;

    @Column(
            name = "CBTDIV_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTDIV_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTDIV_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTDIV_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTDIV_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTDIV_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "division", fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
