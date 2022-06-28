package org.crsoft.cartonplast.users.controller;

import lombok.RequiredArgsConstructor;
import org.crsoft.cartonplast.users.service.impl.PersonService;
import org.crsoft.cartonplast.users.vo.res.PersonRes;
import org.crsoft.cartonplast.users.vo.res.ProfileRes;
import org.crsoft.cartonplast.users.vo.res.ShortPersonRes;
import org.crsoft.cartonplast.users.vo.res.UserImageRes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.crsoft.cartonplast.users.constant.GlobalConstant.V1_API_VERSION;

/**
 * @author lpillaga on 31/05/2022
 */
@RequiredArgsConstructor
@RestController
@RequestMapping(V1_API_VERSION + "/persons")
public class PersonController {

    private final PersonService personService;

    @GetMapping
    public ResponseEntity<List<PersonRes>> findAllPersons() {
        return ResponseEntity.ok(personService.findAllValidPersons());
    }

    @GetMapping("/search-by-userid/{userId}")
    public ResponseEntity<ShortPersonRes> findPersonByUserId(
            @PathVariable String userId) {
        return ResponseEntity.ok(personService.findPersonByUserId(userId));
    }

    @GetMapping("/search")
    public ResponseEntity<List<ShortPersonRes>> findAllPersons(
            @RequestParam(value = "query") String query) {
        return ResponseEntity.ok(personService.findByQuery(query));
    }

    @GetMapping("/profile/{username}")
    public ResponseEntity<ProfileRes> profile(
            @PathVariable String username) {
        return ResponseEntity.ok(personService.findProfileByUsername(username));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Boolean> deletePerson(
            @PathVariable("id") Integer id) {
        return ResponseEntity.ok(personService.delete(id));
    }

    @GetMapping("/image/{username}")
    public ResponseEntity<UserImageRes> getImage(
            @PathVariable String username) {
        return ResponseEntity.ok(personService.getImage(username));
    }
}
