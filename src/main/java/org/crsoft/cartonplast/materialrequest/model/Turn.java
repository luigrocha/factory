package org.crsoft.cartonplast.materialrequest.model;

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
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(name = "CFTTURN")
public class Turn {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CFTTURN_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CFTTURN_NAME",
            nullable = false,
            length = 32
    )
    private String name;

    @Column(
            name = "CFTTURN_START_TIME",
            nullable = false
    )
    private LocalTime startTime;

    @Column(
            name = "CFTTURN_END_TIME",
            nullable = false
    )
    private LocalTime endTime;

    @Column(
            name = "CFTTURN_IS_ACTIVE",
            nullable = false
    )
    private Boolean isActive;

    @Column(
            name = "CFTTURN_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CFTTURN_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(
            name = "CFTTURN_CREATED_BY",
            length = 16
    )
    @CreatedBy
    private String createdBy;

    @Column(
            name = "CFTTURN_UPDATED_BY",
            length = 16
    )
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CFTTURN_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CFTTURN_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;

    @OneToMany(
            mappedBy = "turn",
            fetch = FetchType.LAZY
    )
    private List<MaterialRequest> materialRequests = new ArrayList<>();
}
