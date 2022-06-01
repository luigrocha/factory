package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDate;
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
@Table(name = "CBTPERSON")
public class Person {

    @Id
    @Column(
            name = "ID_CBTPERSON_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CBTPERSON_CI",
            length = 10,
            nullable = false
    )
    private String ci;

    @Column(
            name = "CBTPERSON_FIRST_LASTNAME",
            length = 64,
            nullable = false
    )
    private String firstLastName;

    @Column(
            name = "CBTPERSON_SECOND_LASTNAME",
            length = 64
    )
    private String secondLastName;

    @Column(
            name = "CBTPERSON_FIRST_NAME",
            length = 64,
            nullable = false
    )
    private String firstName;

    @Column(
            name = "CBTPERSON_SECOND_NAME",
            length = 64
    )
    private String secondName;

    @Column(
            name = "CBTPERSON_BIRTH_DATE"
    )
    private LocalDate birthDate;

    @Column(
            name = "CBTPERSON_MOVIL_1",
            length = 16
    )
    private String mobil1;

    @Column(
            name = "CBTPERSON_MOVIL_2",
            length = 16
    )
    private String mobil2;

    @Column(
            name = "CBTPERSON_EMAIL_1",
            length = 64
    )
    private String email1;

    @Column(
            name = "CBTPERSON_EMAIL_2",
            length = 64
    )
    private String email2;

    @Column(
            name = "CBTPERSON_ADDRESS",
            length = 256
    )
    private String address;

    @Column(
            name = "CBTPERSON_LANDLINE",
            length = 16
    )
    private String landline;

    @Column(
            name = "CBTPERSON_CONTRACT_DATE",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDate contractDate;

    @Column(
            name = "CBTPERSON_OBSERVATION",
            length = 256
    )
    private String observation;

    @Column(
            name = "CBTPERSON_ENFORCE",
            nullable = false
    )
    private Integer enforce;

    @Column(
            name = "CBTPERSON_IMAGE_NAME",
            length = 128
    )
    private String imageName;

    @Column(
            name = "CBTPERSON_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTPERSON_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTPERSON_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTPERSON_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTPERSON_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTPERSON_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTETH_CODE",
            referencedColumnName = "ID_CBTETH_CODE"
    )
    private Ethnic ethnic;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTPERGR_CODE",
            referencedColumnName = "ID_CBTPERGR_CODE",
            nullable = false
    )
    private Group group;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTGEN_CODE",
            referencedColumnName = "ID_CBTGEN_CODE",
            nullable = false
    )
    private Gender gender;

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<EmergencyContact> emergencyContacts = new ArrayList<>();

    @OneToMany(mappedBy = "person", fetch = FetchType.LAZY)
    private List<User> users = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
