package org.crsoft.cartonplast.die;

import org.crsoft.cartonplast.design.model.Die;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DieRepository extends PagingAndSortingRepository<Die, Integer> {

    @Query("SELECT d FROM Die d " +
            "WHERE (d.validTo IS NULL " +
            "OR d.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY d.name DESC")
    List<Die> findAllValidDies();

    // TODO Restar -1  dia paremetrizable al valid to
}
