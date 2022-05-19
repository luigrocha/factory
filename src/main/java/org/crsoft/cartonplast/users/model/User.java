package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * User Model
 *
 * @author jyepez on 2/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTUSE", schema = "cartonplast_users_test")
public class User {

    @Id
    @Column(name = "ID_CBTUSE_CODE")
    private String code;

    @Column(name = "CBTUSE_USERNAME")
    private String username;

    @Column(name = "CBTUSE_PHOTO")
    private String photo;

    @Column(name = "CBTUSE_PHONE")
    private String phone;

    @Column(name = "CBTUSE_ADDRESS")
    private String address;

    @Column(name = "CBTUSE_VALID_FROM")
    private Timestamp validFrom;

    @Column(name = "CBTUSE_VALID_TO")
    private Timestamp validTo;

    @Column(name = "CBTUSE_CREATED_BY")
    private String createdBy;

    @Column(name = "CBTUSE_UPDATED_BY")
    private String updatedBy;

    @Column(name = "CBTUSE_CREATED_AT")
    private Timestamp createdAt;

    @Column(name = "CBTUSE_UPDATED_AT")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "XID_CBTPRE_CODE", referencedColumnName = "ID_CBTPRE_CODE", nullable = false)
    private Preferences preferences;
}
