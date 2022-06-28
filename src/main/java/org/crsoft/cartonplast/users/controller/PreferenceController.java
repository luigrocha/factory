package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.model.Preferences;
import org.crsoft.cartonplast.users.service.IUserService;
import org.crsoft.cartonplast.users.service.impl.PreferencesService;
import org.crsoft.cartonplast.users.service.impl.UserService;
import org.crsoft.cartonplast.users.vo.req.PreferencesReq;
import org.crsoft.cartonplast.users.vo.res.PreferencesRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;


/**
 * Preference Controller
 *
 * @author jyepez on 2/5/2022
 */
@RestController
@RequestMapping(V1_API_VERSION + "/users/{username}/preferences")
@RequiredArgsConstructor
public class PreferenceController {

    private final PreferencesService preferencesService;

    @GetMapping
    @RolesAllowed({"backend-admin", "backend-user", "backend-supervisor"})
    public ResponseEntity<PreferencesRes> findPreferencesByUsername(
            @PathVariable String username) {
        return ResponseEntity.ok().body(this.preferencesService.findPreferencesByUsername(username));
    }

    @PatchMapping
    @RolesAllowed({"backend-admin", "backend-user", "backend-supervisor"})
    public ResponseEntity<Void> updatePreferencesByUsername(
            @PathVariable String username,
            @RequestBody PreferencesReq preferences) {
        this.preferencesService.updatePreferences(username, preferences);
        return ResponseEntity.ok().build();
    }
}
