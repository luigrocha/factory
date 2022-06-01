package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * User Model
 *
 * @author jyepez on 2/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTUSE")
public class User {

    @Id
    @Column(name = "ID_CBTUSE_CODE")
    private String code;

    @Column(name = "CBTUSE_USERNAME")
    private String username;

    @Column(name = "CBTUSE_VALID_FROM")
    private LocalDateTime validFrom;

    @Column(name = "CBTUSE_VALID_TO")
    private LocalDateTime validTo;

    @Column(name = "CBTUSE_CREATED_BY")
    private String createdBy;

    @Column(name = "CBTUSE_UPDATED_BY")
    private String updatedBy;

    @Column(name = "CBTUSE_CREATED_AT")
    private LocalDateTime createdAt;

    @Column(name = "CBTUSE_UPDATED_AT")
    private LocalDateTime updatedAt;

    @OneToOne
    @JoinColumn(name = "XID_CBTPRE_CODE", referencedColumnName = "ID_CBTPRE_CODE", nullable = false)
    private Preferences preferences;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTPERSON_CODE",
            referencedColumnName = "ID_CBTPERSON_CODE"
    )
    private Person person;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
