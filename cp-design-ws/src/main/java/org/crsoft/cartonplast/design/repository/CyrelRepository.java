package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.Cyrel;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

/**
 * @author lpillaga on 12/05/2022
 */
public interface CyrelRepository extends PagingAndSortingRepository<Cyrel, Integer> {

    @Query("SELECT c FROM Cyrel c " +
            "WHERE (c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP) AND " +
            "((:query IS NULL OR c.print LIKE CONCAT('%',:query ,'%')) OR " +
            "(:query IS NULL OR c.description LIKE CONCAT('%',:query ,'%'))) " +
            "ORDER BY c.print ASC")
    Page<Cyrel> findAllValidCyrels(Pageable pageable, @Param("query") String query);
}
