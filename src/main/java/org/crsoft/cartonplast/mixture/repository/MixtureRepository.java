package org.crsoft.cartonplast.mixture.repository;

import org.crsoft.cartonplast.mixture.model.Mixture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 3/7/2022
 */
public interface MixtureRepository extends JpaRepository<Mixture, Integer> {

    @Query("SELECT p FROM Mixture p " +
            "WHERE (p.validTo IS NULL OR " +
            "p.validTo > CURRENT_TIMESTAMP) AND " +
            "(p.order.lot LIKE %?1% OR " +
            "p.order.code LIKE %?1% OR " +
            "p.number LIKE %?1% OR " +
            "p.mixture LIKE %?1% OR " +
            "p.order.productCode LIKE %?1%) " +
            "ORDER BY p.id ASC")
    Collection<Mixture> findAllValidMixtureFromQuery(String query);

    @Query("SELECT m FROM Mixture m " +
            "WHERE (m.validTo IS NULL OR " +
            "m.validTo > CURRENT_TIMESTAMP ) AND " +
            "m.number = :number ")
    Mixture findValidMixtureByNumber(Integer number);

    @Query("SELECT m FROM Mixture m " +
            "WHERE (m.validTo IS NULL OR " +
            "m.validTo > CURRENT_TIMESTAMP ) AND " +
            "m.order.lot = :lot ")
    Mixture findValidMixtureByLot(String lot);
}
