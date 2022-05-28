package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 27/5/2022
 */
@Repository
public interface TypeMaterialRepository extends JpaRepository<TypeMaterial, Integer> {

    Collection<TypeMaterial> findAllByValidToIsNullOrderByIdAsc();

    Optional<TypeMaterial> findByIdAndValidToIsNull(Integer id);

}
