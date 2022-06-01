package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Division;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Repository
public interface DivisionRepository extends JpaRepository<Division, String> {

    @Query("SELECT d FROM Division d " +
            "WHERE d.validTo IS NULL OR " +
            "d.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY d.name ASC")
    List<Division> findAllValidDivisions();
}
