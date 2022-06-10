package org.crsoft.cartonplast.design.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 13/05/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CATCOLTI")
public class ColorType {

    @Id
    @Column(
            name = "ID_CATCOLTI_CODE",
            nullable = false,
            updatable = false,
            length = 2
    )
    private String id;

    @Column(
            name = "CATCOLTI_NAME",
            nullable = false,
            length = 32
    )
    private String name;

    @Column(
            name = "CATCOLTI_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATCOLTI_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATCOLTI_CREATED_BY", length = 16)
    private String createdBy;

    @Column(name = "CATCOLTI_UPDATED_BY", length = 16)
    private String updatedBy;

    @Column(
            name = "CATCOLTI_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATCOLTI_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "colorType",
            fetch = FetchType.LAZY
    )
    private List<CyrelColor> cyrelColors = new ArrayList<>();
}
