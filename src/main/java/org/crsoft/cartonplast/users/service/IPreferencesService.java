package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.model.Preferences;
import org.crsoft.cartonplast.users.vo.req.PreferencesReq;
import org.crsoft.cartonplast.users.vo.res.PreferencesRes;

/**
 * @author lpillaga on 26/06/2022
 */
public interface IPreferencesService {

    Preferences getDefaultPreferences();

    PreferencesRes findPreferencesByUsername(String username);

    void updatePreferences(String username, PreferencesReq preferencesReq);
}
