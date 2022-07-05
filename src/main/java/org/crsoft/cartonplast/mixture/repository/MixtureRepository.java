package org.crsoft.cartonplast.mixture.repository;

import org.crsoft.cartonplast.mixture.model.Mixture;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jyepez on 3/7/2022
 */
public interface MixtureRepository extends JpaRepository<Mixture, Integer> {
}
