package org.crsoft.cartonplast.celler.repository;

import org.crsoft.cartonplast.celler.model.Document;
import org.crsoft.cartonplast.celler.model.OptionDocument;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;

/**
 * @author jyepez on 2/6/2022
 */
@Repository
public interface OptionDocumentRepository extends JpaRepository<OptionDocument, Integer> {

    Collection<OptionDocument> findAllByDocumentAndValidToIsNullOrderByCreatedAtAsc(Document document);

}
