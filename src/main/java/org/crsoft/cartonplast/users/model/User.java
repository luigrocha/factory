package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

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
@Builder
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CBTUSE")
public class User {

    @Id
    @Column(name = "ID_CBTUSE_CODE")
    private String code;

    @Column(name = "CBTUSE_USERNAME")
    private String username;

    @Column(name = "CBTUSE_VALID_FROM")
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(name = "CBTUSE_VALID_TO")
    private LocalDateTime validTo;

    @Column(name = "CBTUSE_CREATED_BY")
    @CreatedBy
    private String createdBy;

    @Column(name = "CBTUSE_UPDATED_BY")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "CBTUSE_CREATED_AT")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "CBTUSE_UPDATED_AT")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(
            name = "XID_CBTPRE_CODE",
            referencedColumnName = "ID_CBTPRE_CODE",
            nullable = false
    )
    private Preferences preferences;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(
            name = "XID_CBTPERSON_CODE",
            referencedColumnName = "ID_CBTPERSON_CODE"
    )
    private Person person;
}
