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
@Table(name = "CBTPERGR")
public class Group {

    @Id
    @Column(
            name = "ID_CBTPERGR_CODE",
            length = 3,
            updatable = false,
            nullable = false
    )
    private String id;

    @Column(
            name = "CBTPERGR_NAME",
            length = 32,
            nullable = false
    )
    private String name;

    @Column(
            name = "CBTPERGR_CH",
            nullable = false
    )
    private Boolean ch;

    @Column(
            name = "CBTPERGR_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validFrom;

    @Column(
            name = "CBTPERGR_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CBTPERGR_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CBTPERGR_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CBTPERGR_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime createdAt;

    @Column(
            name = "CBTPERGR_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime updatedAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTDIV_CODE",
            referencedColumnName = "ID_CBTDIV_CODE",
            nullable = false
    )
    private Division division;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTFIRSTGR_CODE",
            referencedColumnName = "ID_CBTFIRSTGR_CODE",
            nullable = false
    )
    private FirstDigit firstDigit;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTSECONDGR_CODE",
            referencedColumnName = "ID_CBTSECONDGR_CODE",
            nullable = false
    )
    private SecondDigit secondDigit;

    @OneToMany(mappedBy = "group", fetch = FetchType.LAZY)
    private List<Person> persons = new ArrayList<>();

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
