package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * User Model
 *
 * @author jyepez on 2/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTUSE", schema = "carton_plast_test")
public class User {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CBTUSE_CODE")
    private Integer code;

    @Basic
    @Column(name = "CBTUSE_USERNAME")
    private String username;

    @Basic
    @Column(name = "CBTUSE_PHOTO")
    private String photo;

    @Basic
    @Column(name = "CBTUSE_PHONE")
    private String phone;

    @Basic
    @Column(name = "CBTUSE_ADDRESS")
    private String address;

    @Basic
    @Column(name = "CBTUSE_VALID_FROM")
    private Timestamp validFrom;

    @Basic
    @Column(name = "CBTUSE_VALID_TO")
    private Timestamp validTo;

    @Basic
    @Column(name = "CBTUSE_CREATED_BY")
    private String createdBy;

    @Basic
    @Column(name = "CBTUSE_UPDATED_BY")
    private String updatedBy;

    @Basic
    @Column(name = "CBTUSE_CREATED_AT")
    private Timestamp createdAt;

    @Basic
    @Column(name = "CBTUSE_UPDATED_AT")
    private Timestamp updatedAt;

    @ManyToOne
    @JoinColumn(name = "CBTPRE_CODE", referencedColumnName = "CBTPRE_CODE", nullable = false)
    private Preferences preferences;
}
