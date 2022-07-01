package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.DieDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 22/06/2022
 */
@Repository
public interface DieDocumentRepository extends JpaRepository<DieDocument, Integer> {

    @Query("SELECT d FROM DieDocument d " +
            "WHERE (d.validTo IS NULL " +
            "OR d.validTo > CURRENT_TIMESTAMP) " +
            "AND d.die.id = ?1 " +
            "ORDER BY d.version DESC")
    List<DieDocument> findAllValidDocumentsByDieId(Integer dieId);

    @Query("SELECT COALESCE(MAX(d.version), 0) " +
            "FROM DieDocument d " +
            "WHERE d.die.id = ?1")
    int findLastVersionNumberByDieId(Integer dieId);
}
