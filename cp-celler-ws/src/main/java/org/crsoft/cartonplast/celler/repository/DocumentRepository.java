package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Document;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

/**
 * @author jyepez on 31/5/2022
 */
@Repository
public interface DocumentRepository extends JpaRepository<Document, Integer> {

    Collection<Document> findAllByValidToIsNullOrderByNameAsc();

    Optional<Document> findByIdAndValidToIsNull(Integer code);

}
