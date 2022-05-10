package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 02/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(
        name = "CATMAQ",
        indexes = {
                @Index(name = "CAIMAQ_NAME", columnList = "CATMAQ_NAME"),
        }
)
public class Machine {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATMAQ_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATMAQ_NAME",
            nullable = false,
            length = 32
    )
    private String name;

    @Column(
            name = "CATMAQ_HAS_DESB",
            nullable = false
    )
    private Boolean hasDesb;

    @OneToMany(
            mappedBy = "machine",
            fetch = FetchType.LAZY
    )
    private List<DieMachine> dies = new ArrayList<>();

    @Column(name = "CATMAQ_VALID_FROM")
    private LocalDateTime validFrom;

    @Column(name = "CATMAQ_VALID_TO")
    private LocalDateTime validTo;

    @Column(name = "CATMAQ_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATMAQ_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(name = "CATMAQ_CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CATMAQ_UPDATED_AT")
    private LocalDateTime updatedAt;

    @OneToMany(
            fetch = FetchType.LAZY,
            mappedBy = "machine"
    )
    private List<DieMachine> dieMachines = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
