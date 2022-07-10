package org.crsoft.cartonplast.mixture.repository;

import org.crsoft.cartonplast.mixture.model.MixtureDetail;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;

/**
 * @author jyepez on 5/7/2022
 */
public interface MixtureDetailRepository extends JpaRepository<MixtureDetail, Integer> {

    @Query("SELECT m FROM MixtureDetail m " +
            "WHERE (m.validTo IS NULL OR " +
            "m.validTo > CURRENT_TIMESTAMP ) AND " +
            "m.mixture.id = :mixtureCode " +
            "ORDER BY m.id ASC ")
    Collection<MixtureDetail> findAllValidMixtureByMixtureCode(Integer mixtureCode);

}
