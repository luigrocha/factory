package org.crsoft.cartonplast.users.repository;

import org.crsoft.cartonplast.users.model.Preferences;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * @author jyepez on 2/5/2022
 */
public interface PreferencesRepository extends JpaRepository<Preferences, Integer> {
}
