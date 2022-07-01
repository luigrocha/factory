package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 27/5/2022
 */
@Repository
public interface MaterialRepository extends JpaRepository<Material, Integer> {

    Collection<Material> findAllByTypeMaterialAndValidToIsNullOrderByNameAsc(TypeMaterial typeMaterial);

    Optional<Material> findByIdAndValidToIsNull(Integer code);

    Optional<Material> findByNameAndValidToIsNull(Integer code);

}
