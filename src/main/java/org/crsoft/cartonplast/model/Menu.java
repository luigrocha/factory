package org.crsoft.cartonplast.model;

/**
 * @author jyepez on 6/5/2022
 */

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Menu Model
 *
 * @author jyepez on 5/5/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "CBTMEN", schema = "carton_plast_test")
public class Menu {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "CBTMEN_CODE")
    private Integer code;

    @Basic
    @Column(name = "CBTMEN_LABEL")
    private String label;

    @Basic
    @Column(name = "CBTMEN_DATA")
    private String data;

    @Basic
    @Column(name = "CBTMEN_ICON")
    private String icon;

    @Basic
    @Column(name = "CBTMEN_URL")
    private String url;

    @Basic
    @Column(name = "CBTMEN_ROLE")
    private String role;

    @Basic
    @Column(name = "CBTMEN_CONDITION")
    private Boolean condition;

    @Basic
    @Column(name = "CBTMEN_ORDER")
    private Integer order;

    @Basic
    @Column(name = "CBTMEN_VALID_FROM")
    private LocalDateTime validFrom;

    @Basic
    @Column(name = "CBTMEN_VALID_TO")
    private LocalDateTime validTo;

    @Basic
    @Column(name = "CBTMEN_CREATED_BY")
    private String createdBy;

    @Basic
    @Column(name = "CBTMEN_UPDATED_BY")
    private String updatedBy;

    @Basic
    @Column(name = "CBTMEN_CREATED_AT")
    private LocalDateTime createdAt;

    @Basic
    @Column(name = "CBTMEN_UPDATED_AT")
    private LocalDateTime updateAt;

    @ManyToOne
    @JoinColumn(name = "CBT_CBTMEN_CODE")
    private Menu child;

    @PrePersist
    public void prePersist() {
        this.validFrom = LocalDateTime.now();
        this.createdAt = LocalDateTime.now();
    }
}
