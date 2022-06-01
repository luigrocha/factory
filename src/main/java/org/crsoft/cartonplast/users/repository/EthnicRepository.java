package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Ethnic;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Repository
public interface EthnicRepository extends JpaRepository<Ethnic, String> {

    @Query("SELECT e FROM Ethnic e " +
            "WHERE e.validTo IS NULL OR " +
            "e.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY e.name ASC")
    List<Ethnic> findAllValidEthnics();
}
