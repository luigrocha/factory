package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author lpillaga on 11/05/2022
 */
@Repository
public interface HomoPolymerRepository extends JpaRepository<HomoPolymer, Integer> {

    Collection<HomoPolymer> findAllByValidToIsNullOrderByPercentageAsc();

    Optional<HomoPolymer> findByIdAndValidToIsNull(Integer code);

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM HomoPolymer c " +
            "WHERE (c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP) " +
            "AND c.hpCode = :code")
    boolean existsByHpCodeAndIsActive(
            @Param("code") String hpCode);
}
