package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 31/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTEMCON")
public class EmergencyContact {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CBTEMCON_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CBTEMCON_CONTACT",
            length = 64
    )
    private String contact;

    @Column(
            name = "CBTEMCON_PHONE",
            length = 16
    )
    private String phone;

    @Column(
            name = "CBTEMCON_PHONE_2",
            length = 16
    )
    private String phone2;

    @Column(
            name = "CBTEMCON_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTEMCON_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTEMCON_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTEMCON_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTEMCON_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTEMCON_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTREL_CODE",
            referencedColumnName = "ID_CBTREL_CODE"
    )
    private Relationship relationship;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTPERSON_CODE",
            referencedColumnName = "ID_CBTPERSON_CODE",
            nullable = false
    )
    private Person person;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
