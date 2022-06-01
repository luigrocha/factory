package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.SecondDigit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Repository
public interface SecondDigitRepository extends JpaRepository<SecondDigit, String> {

    @Query("SELECT sd FROM SecondDigit sd " +
            "WHERE sd.validTo IS NULL OR " +
            "sd.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY sd.name ASC")
    List<SecondDigit> findAllValidSecondDigits();
}
