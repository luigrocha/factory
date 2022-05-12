package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.ColorA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface ColorARepository extends JpaRepository<ColorA, String> {

    @Query("SELECT c FROM ColorA c " +
            "WHERE c.validTo IS NULL OR " +
            "c.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY c.name ASC")
    List<ColorA> findAllValidColors();
}
