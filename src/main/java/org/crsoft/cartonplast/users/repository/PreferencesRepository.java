package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author jyepez on 2/5/2022
 */
@Repository
public interface PreferencesRepository extends JpaRepository<Preferences, Integer> {

    @Query("SELECT p FROM Preferences p WHERE p.user.username = ?1")
    Optional<Preferences> findByUsername(String username);
}
