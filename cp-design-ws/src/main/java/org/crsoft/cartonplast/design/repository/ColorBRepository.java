package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.ColorB;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface ColorBRepository extends JpaRepository<ColorB, String> {

    @Query("SELECT c FROM ColorB c " +
            "WHERE c.validTo IS NULL OR " +
            "c.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY c.id ASC, c.index ASC")
    List<ColorB> findAllValidColors();
}
