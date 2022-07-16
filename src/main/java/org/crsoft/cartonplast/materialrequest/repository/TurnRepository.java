package org.crsoft.cartonplast.materialrequest.repository;

import org.crsoft.cartonplast.materialrequest.model.Turn;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 15/07/2022
 */
@Repository
public interface TurnRepository extends JpaRepository<Turn, Integer> {

    @Query("SELECT t FROM Turn t " +
            "WHERE (t.validTo IS NULL " +
            "OR t.validTo > CURRENT_TIMESTAMP) " +
            "AND (:isActive IS NULL OR t.isActive = :isActive) ")
    List<Turn> findAllValidTurns(@Param("isActive") Boolean isActive);
}
