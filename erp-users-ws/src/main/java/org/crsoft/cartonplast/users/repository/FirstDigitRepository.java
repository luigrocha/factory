package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.FirstDigit;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 30/05/2022
 */
@Repository
public interface FirstDigitRepository extends JpaRepository<FirstDigit, String> {

    @Query("SELECT fd FROM FirstDigit fd " +
            "WHERE fd.validTo IS NULL OR " +
            "fd.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY fd.name ASC")
    List<FirstDigit> findAllValidFirstDigits();
}
