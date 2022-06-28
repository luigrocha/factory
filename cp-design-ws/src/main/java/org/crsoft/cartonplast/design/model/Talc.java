package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;

/**
 * @author lpillaga on 23/06/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATCTAL")
public class Talc {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATCTAL_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATCTAL_PERCENT",
            nullable = false
    )
    private Integer percentage;

    @Column(
            name = "CATCTAL_LP_CODE",
            length = 4,
            nullable = false
    )
    private String lpCode;

    @Column(
            name = "CATCTAL_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATCTAL_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCTAL_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCTAL_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCTAL_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATCTAL_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
