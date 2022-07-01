package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.CyrelDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 20/06/2022
 */
@Repository
public interface CyrelDocumentRepository extends JpaRepository<CyrelDocument, Integer> {

    @Query("SELECT d FROM CyrelDocument d " +
            "WHERE (d.validTo IS NULL " +
            "OR d.validTo > CURRENT_TIMESTAMP) " +
            "AND d.cyrel.id = ?1 " +
            "ORDER BY d.version DESC")
    List<CyrelDocument> findAllValidDocumentsByCyrelId(Integer cyrelId);

    @Query("SELECT COALESCE(MAX(d.version), 0) " +
            "FROM CyrelDocument d " +
            "WHERE d.cyrel.id = ?1")
    int findLastVersionNumberByCyrelId(Integer cyrelId);
}
