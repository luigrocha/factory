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
@Table(name = "CATCTIPPRO")
public class ProjectType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATCTIPPRO_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATCTIPPRO_NAME",
            nullable = false,
            length = 32
    )
    private String name;

    @Column(
            name = "CATCTIPPRO_CODE",
            length = 8
    )
    private String code;

    @Column(
            name = "CATCTIPPRO_UNIQUE_CODE",
            nullable = false,
            length = 2
    )
    private String uniqueCode;

    @Column(
            name = "CATCTIPPRO_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATCTIPPRO_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCTIPPRO_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCTIPPRO_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCTIPPRO_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATCTIPPRO_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
