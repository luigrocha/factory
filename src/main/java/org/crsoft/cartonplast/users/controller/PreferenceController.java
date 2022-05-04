package org.crsoft.cartonplast.users.controller;

import org.crsoft.cartonplast.users.exception.NotFoundException;
import org.crsoft.cartonplast.users.exception.UpdateException;
import org.crsoft.cartonplast.users.model.Preferences;
import org.crsoft.cartonplast.users.service.IUserService;
import org.crsoft.cartonplast.users.vo.res.MessageRes;
import org.crsoft.cartonplast.users.vo.res.PreferencesRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.annotation.security.RolesAllowed;

import static org.crsoft.cartonplast.users.util.Constants.*;

/**
 * Preference Controller
 *
 * @author jyepez on 2/5/2022
 */
@RestController
@RequestMapping("/preference")
@CrossOrigin(origins = {CROSS_LOCAL, CROSS_DEVELOP, CROSS_PRODUCTION})
public class PreferenceController {

    private final IUserService userService;

    public PreferenceController(IUserService userService) {
        this.userService = userService;
    }

    /**
     * Find Preferences By Username
     *
     * @param userName userName
     * @return PreferencesRes
     */
    @GetMapping("/findPreferencesByUsername/{userName}")
    @RolesAllowed({"backend-admin","backend-user", "backend-supervisor"})
    public ResponseEntity<PreferencesRes> findPreferencesByUsername(@PathVariable String userName) {
        try {
            return ResponseEntity.ok().body(this.userService.findPreferencesByUsername(userName));
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        }
    }

    /**
     * Update Preferences By Username
     *
     * @param userName    userName
     * @param preferences preferences
     * @return void
     */
    @PatchMapping("/updatePreferencesByUsername/{userName}")
    @RolesAllowed({"backend-admin","backend-user", "backend-supervisor"})
    public ResponseEntity<?> updatePreferencesByUsername(@PathVariable String userName, @RequestBody Preferences preferences) {
        try {
            this.userService.updatePreferencesByUsername(userName, preferences);
            return ResponseEntity.ok().build();
        } catch (NotFoundException e) {
            return ResponseEntity.notFound().build();
        } catch (UpdateException e) {
            return ResponseEntity.badRequest().body(MessageRes.builder().message(e.getMessage()).build());
        }
    }
}
