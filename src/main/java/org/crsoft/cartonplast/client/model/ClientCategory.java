package org.crsoft.cartonplast.client.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CATCAT_CLI")
public class ClientCategory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATCAT_CLI_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATCAT_CLI_NAME",
            nullable = false,
            length = 32
    )
    private String name;

    @Column(
            name = "CATCAT_CLI_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATCAT_CLI_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCAT_CLI_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCAT_CLI_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCAT_CLI_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATCAT_CLI_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "category",
            fetch = FetchType.LAZY
    )
    private List<Client> clients = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
