package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Celler;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 31/5/2022
 */
@Repository
public interface CellerRepository extends JpaRepository<Celler, Integer> {

    Collection<Celler> findAllByValidToIsNullOrderByCreatedAtDesc();

    Optional<Celler> findByIdAndValidToIsNull(Integer code);

}
