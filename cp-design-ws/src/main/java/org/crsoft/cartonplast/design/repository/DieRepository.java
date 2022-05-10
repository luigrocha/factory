package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.Die;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface DieRepository extends PagingAndSortingRepository<Die, Integer> {

    @Query("SELECT d FROM Die d " +
            "WHERE (d.validTo IS NULL " +
            "OR d.validTo > CURRENT_TIMESTAMP) AND " +
            "((:query IS NULL OR d.code LIKE CONCAT('%',:query ,'%')) OR " +
            "(:query IS NULL OR d.name LIKE CONCAT('%',:query ,'%')) OR " +
            "(:query IS NULL OR d.description LIKE CONCAT('%',:query ,'%'))) " +
            "ORDER BY d.createdDate DESC")
    Page<Die> findAllValidDies(Pageable pageable, @Param("query") String query);
}
