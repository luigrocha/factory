package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface HomoPolymerRepository extends JpaRepository<HomoPolymer, Integer> {

    @Query("SELECT h FROM HomoPolymer h " +
            "WHERE h.validTo IS NULL OR " +
            "h.validTo > CURRENT_TIMESTAMP ORDER BY h.percentage ASC")
    List<HomoPolymer> findAllValidHomoPolymers();
}
