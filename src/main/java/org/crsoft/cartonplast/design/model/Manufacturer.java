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

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@EntityListeners(AuditingEntityListener.class)
@Table(
        name = "CATFAB",
        indexes = {
                @Index(name = "CAIFAB_NAME", columnList = "CATFAB_NAME"),
        }
)
public class Manufacturer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(
            name = "ID_CATFAB_CODE",
            updatable = false,
            nullable = false
    )
    private Integer id;

    @Column(
            name = "CATFAB_NAME",
            nullable = false,
            length = 32
    )
    private String name;

    @Column(
            name = "CATFAB_VALID_FROM",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime validFrom;

    @Column(
            name = "CATFAB_VALID_TO",
            columnDefinition = "TIMESTAMP"
    )
    private LocalDateTime validTo;

    @Column(name = "CATFAB_CREATED_BY", length = 16)
    @CreatedBy
    private String createdBy;

    @Column(name = "CATFAB_UPDATED_BY", length = 16)
    @LastModifiedBy
    private String updatedBy;

    @Column(
            name = "CATFAB_CREATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @CreatedDate
    private LocalDateTime createdAt;

    @Column(
            name = "CATFAB_UPDATED_AT",
            columnDefinition = "TIMESTAMP"
    )
    @LastModifiedDate
    private LocalDateTime updatedAt;
}
