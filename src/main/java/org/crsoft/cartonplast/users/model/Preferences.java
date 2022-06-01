package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Timestamp;

import static javax.persistence.GenerationType.IDENTITY;


/**
 * Preferences Model
 *
 * @author jyepez on 2/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTPRE")
public class Preferences {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CBTPRE_CODE")
    private Integer code;

    @Basic
    @Column(name = "CBTPRE_COLOR_MODE")
    private String colorMode;

    @Basic
    @Column(name = "CBTPRE_MENU_MODE")
    private String menuMode;

    @Basic
    @Column(name = "CBTPRE_MENU_THEME")
    private String menuTheme;

    @Basic
    @Column(name = "CBTPRE_TOPBAR_MODE")
    private String topBarMode;

    @Basic
    @Column(name = "CBTPRE_COLOR")
    private String color;

    @Basic
    @Column(name = "CBTPRE_VALID_FROM")
    private Timestamp validFrom;

    @Basic
    @Column(name = "CBTPRE_VALID_TO")
    private Timestamp validTo;

    @Basic
    @Column(name = "CBTPRE_CREATED_BY")
    private String createdBy;

    @Basic
    @Column(name = "CBTPRE_UPDATED_BY")
    private String updatedBy;

    @Basic
    @Column(name = "CBTPRE_CREATED_AT")
    private Timestamp createdAt;

    @Basic
    @Column(name = "CBTPRE_UPDATED_AT")
    private Timestamp updatedAt;
}
