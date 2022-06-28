package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.exception.BusinessException;
import org.crsoft.cartonplast.users.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.users.model.Preferences;
import org.crsoft.cartonplast.users.model.User;
import org.crsoft.cartonplast.users.repository.PreferencesRepository;
import org.crsoft.cartonplast.users.repository.UserRepository;
import org.crsoft.cartonplast.users.service.IPreferencesService;
import org.crsoft.cartonplast.users.service.mapper.PreferencesMapper;
import org.crsoft.cartonplast.users.vo.req.PreferencesReq;
import org.crsoft.cartonplast.users.vo.res.PreferencesRes;
import org.springframework.stereotype.Service;

import static org.crsoft.cartonplast.users.constant.PreferencesConstant.*;

/**
 * @author lpillaga on 26/06/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class PreferencesService implements IPreferencesService {

    private final UserRepository userRepository;
    private final PreferencesRepository preferencesRepository;
    private final PreferencesMapper preferencesMapper;

    @Override
    public Preferences getDefaultPreferences() {
        Preferences preferences = new Preferences();
        preferences.setColorMode(DEFAULT_PREFERENCES_COLOR_MODE);
        preferences.setMenuMode(DEFAULT_PREFERENCES_MENU_MODE);
        preferences.setMenuTheme(DEFAULT_PREFERENCES_MENU_THEME);
        preferences.setTopBarMode(DEFAULT_PREFERENCES_TOP_BAR_MODE);
        preferences.setColor(DEFAULT_PREFERENCES_COLOR);
        return preferences;
    }

    @Override
    public PreferencesRes findPreferencesByUsername(String username) {
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("User not found with username: {}", username);
                    return new BusinessException(BusinessExceptionReason.USER_LOCAL_NOT_FOUND);
                });
        return preferencesMapper.preferencesToPreferencesRes(user.getPreferences());
    }

    @Override
    public void updatePreferences(String username, PreferencesReq preferencesReq) {
        Preferences preferences = preferencesRepository.findByUsername(username)
                .orElseThrow(() -> {
                    log.error("Preferences not found with username: {}", username);
                    return new BusinessException(BusinessExceptionReason.PREFERENCES_NOT_FOUND, username);
                });

        preferences.setColorMode(preferencesReq.getColorMode());
        preferences.setMenuMode(preferencesReq.getMenuMode());
        preferences.setMenuTheme(preferencesReq.getMenuTheme());
        preferences.setTopBarMode(preferencesReq.getTopBarMode());
        preferences.setColor(preferencesReq.getColor());
        preferencesRepository.save(preferences);
    }
}
