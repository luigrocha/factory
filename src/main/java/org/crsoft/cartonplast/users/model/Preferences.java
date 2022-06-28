package org.crsoft.cartonplast.users.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.sql.Timestamp;
import java.time.LocalDateTime;

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
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CBTPRE")
public class Preferences {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID_CBTPRE_CODE")
    private Integer code;

    @Column(name = "CBTPRE_COLOR_MODE")
    private String colorMode;

    @Column(name = "CBTPRE_MENU_MODE")
    private String menuMode;

    @Column(name = "CBTPRE_MENU_THEME")
    private String menuTheme;

    @Column(name = "CBTPRE_TOPBAR_MODE")
    private String topBarMode;

    @Column(name = "CBTPRE_COLOR")
    private String color;

    @Column(name = "CBTPRE_VALID_FROM", columnDefinition = "TIMESTAMP")
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(name = "CBTPRE_VALID_TO", columnDefinition = "TIMESTAMP")
    private LocalDateTime validTo;

    @Column(name = "CBTPRE_CREATED_BY")
    @CreatedBy
    private String createdBy;

    @Column(name = "CBTPRE_UPDATED_BY")
    @LastModifiedBy
    private String updatedBy;

    @Column(name = "CBTPRE_CREATED_AT", columnDefinition = "TIMESTAMP")
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(name = "CBTPRE_UPDATED_AT", columnDefinition = "TIMESTAMP")
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToOne(
            mappedBy = "preferences",
            fetch = FetchType.LAZY
    )
    private User user;
}
