package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Gender;
import org.crsoft.cartonplast.users.model.Relationship;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Repository
public interface RelationshipRepository extends JpaRepository<Relationship, String> {

    @Query("SELECT r FROM Relationship r " +
            "WHERE r.validTo IS NULL OR " +
            "r.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY r.name ASC")
    List<Relationship> findAllValidRelationShips();
}
