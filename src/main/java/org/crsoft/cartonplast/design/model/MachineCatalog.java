package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author jyepez on 13/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATMAQ_CAT")
public class MachineCatalog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATMAQ_CAT_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATMAQ_CAT_NAME",
            nullable = false,
            length = 32
    )
    private String name;
    
    @Column(
            name = "CATMAQ_CAT_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATMAQ_CAT_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATMAQ_CAT_CREATED_BY", length = 16)
    @CreatedBy
    private String createdBy;

    @Column(name = "CATMAQ_CAT_UPDATED_BY", length = 16)
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CATMAQ_CAT_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATMAQ_CAT_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;
            
}
