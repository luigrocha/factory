package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Gender;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Repository
public interface GenderRepository extends JpaRepository<Gender, String> {

    @Query("SELECT g FROM Gender g " +
            "WHERE g.validTo IS NULL OR " +
            "g.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY g.name ASC")
    List<Gender> findAllValidGenders();
}
