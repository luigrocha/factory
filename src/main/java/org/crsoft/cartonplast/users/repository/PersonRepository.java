package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    @Query("SELECT p FROM Person p " +
            "WHERE p.validTo IS NULL OR " +
            "p.validTo > CURRENT_TIMESTAMP " +
            "ORDER BY p.id ASC")
    List<Person> findAllValidPersons();
}
