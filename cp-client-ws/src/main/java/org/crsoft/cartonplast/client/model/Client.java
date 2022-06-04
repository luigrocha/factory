package org.crsoft.cartonplast.client.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Builder
@Table(name = "CATCLI")
public class Client {

    @Id
    @Column(
            name = "ID_CATCLI_CODE",
            updatable = false,
            nullable = false,
            length = 16
    )
    private String id;

    @Column(
            name = "CATCLI_NAME",
            nullable = false,
            length = 64
    )
    private String name;

    @Column(
            name = "CATCLI_IMAGE_NAME",
            length = 128
    )
    private String imageName;

    @Column(
            name = "CATCLI_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CATCLI_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCLI_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCLI_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCLI_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CATCLI_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CATCAT_CLI_CODE",
            referencedColumnName = "ID_CATCAT_CLI_CODE",
            insertable = false,
            updatable = false
    )
    private ClientCategory category;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
