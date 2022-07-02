package org.crsoft.cartonplast.design.repository;

import org.crsoft.cartonplast.design.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author lpillaga on 23/06/2022
 */
@Repository
public interface ProjectRepository extends JpaRepository<Project, Integer> {

    @Query("SELECT p FROM Project p " +
            "WHERE (p.validTo IS NULL " +
            "OR p.validTo > CURRENT_TIMESTAMP) " +
            "ORDER BY p.name ASC")
    List<Project> findAllValidProjects();

    @Query("SELECT p FROM Project p " +
            "WHERE (p.validTo IS NULL " +
            "OR p.validTo > CURRENT_TIMESTAMP) AND " +
            "p.client.id = ?1 " +
            "ORDER BY p.name ASC")
    List<Project> findProjectsByClientId(Integer clientId);
}
