package org.crsoft.users.service;

import org.crsoft.users.model.User;
import org.springframework.stereotype.Service;

/**
 * @author jyepez
 */
@Service
public interface IKeycloakService {

    void createUser(User user);

}
