package org.crsoft.cartonplast.client.repository;

import org.crsoft.cartonplast.client.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Repository
public interface ClientRepository extends JpaRepository<Client, Integer> {

    @Query("SELECT c FROM Client c " +
            "WHERE c.validTo IS NULL OR " +
            "c.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY c.name ASC")
    List<Client> findAllValidClients();

    @Query("SELECT CASE WHEN COUNT(c) > 0 THEN true ELSE false END " +
            "FROM Client c " +
            "WHERE (c.validTo IS NULL " +
            "OR c.validTo > CURRENT_TIMESTAMP) " +
            "AND c.code = ?1")
    boolean existsByCodeAndIsNotDeleted(String code);
}
