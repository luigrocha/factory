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
@Table(name = "CBTSECONDGR")
public class SecondDigit {

    @Id
    @Column(
            name = "ID_CBTSECONDGR_CODE",
            length = 2,
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CBTSECONDGR_NAME",
            length = 32,
            nullable = false
    )
    private String name;

    @Column(
            name = "CBTSECONDGR_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTSECONDGR_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTSECONDGR_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTSECONDGR_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTSECONDGR_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTSECONDGR_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(mappedBy = "secondDigit", fetch = FetchType.LAZY)
    private List<Group> groups = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
