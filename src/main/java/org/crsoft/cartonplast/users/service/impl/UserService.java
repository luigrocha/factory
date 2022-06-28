package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.crsoft.cartonplast.users.enums.KeycloakResponseStatus;
import org.crsoft.cartonplast.users.exception.BusinessException;
import org.crsoft.cartonplast.users.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.users.model.Person;
import org.crsoft.cartonplast.users.model.Preferences;
import org.crsoft.cartonplast.users.model.User;
import org.crsoft.cartonplast.users.repository.PersonRepository;
import org.crsoft.cartonplast.users.repository.UserRepository;
import org.crsoft.cartonplast.users.service.IUserService;
import org.crsoft.cartonplast.users.service.mapper.UserMapper;
import org.crsoft.cartonplast.users.util.KeycloakUtil;
import org.crsoft.cartonplast.users.vo.req.GenerateUsernameReq;
import org.crsoft.cartonplast.users.vo.req.CreateUserReq;
import org.crsoft.cartonplast.users.vo.req.UpdateUserReq;
import org.crsoft.cartonplast.users.vo.res.GenerateUsernameRes;
import org.crsoft.cartonplast.users.vo.res.UserRes;
import org.keycloak.admin.client.resource.RealmResource;
import org.keycloak.admin.client.resource.UsersResource;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.RoleRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.ws.rs.core.Response;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Keycloak Service
 *
 * @author jyepez
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UserService implements IUserService {

    private final UserRepository userRepository;
    private final KeycloakUtil keycloakUtil;
    private final PreferencesService preferencesService;
    private final UserMapper userMapper;

    private final PersonRepository personRepository;

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public UserRes createUser(CreateUserReq user) {
        Person person = personRepository.findById(user.getPersonId())
                .orElseThrow(() -> {
                    log.error("Person not found for id: {}", user.getPersonId());
                    return new BusinessException(BusinessExceptionReason.PERSON_NOT_FOUND);
                });

        Response response = null;
        String userIdCode = null;

        try {
            UsersResource usersResource = getUsersResource();
            UserRepresentation userRepresentation = new UserRepresentation();
            userRepresentation.setUsername(user.getUsername());
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setFirstName(user.getFirstName());
            userRepresentation.setLastName(user.getLastName());
            userRepresentation.setEnabled(Boolean.TRUE);

            response = usersResource.create(userRepresentation);
            Integer status = response.getStatus();

            if (KeycloakResponseStatus.OK.getCode().equals(status)) {
                String path = response.getLocation().getPath();
                String userId = path.substring(path.lastIndexOf("/") + 1);
                userIdCode = userId;
                CredentialRepresentation credentialRepresentation = new CredentialRepresentation();
                credentialRepresentation.setTemporary(Boolean.FALSE);
                credentialRepresentation.setType(CredentialRepresentation.PASSWORD);
                credentialRepresentation.setValue(user.getPassword());
                usersResource.get(userId).resetPassword(credentialRepresentation);

                RoleRepresentation roleRepresentation = keycloakUtil.getRealmResource().roles().get("realm-user").toRepresentation();
                keycloakUtil.getRealmResource().users().get(userId).roles().realmLevel().add(List.of(roleRepresentation));

                this.createPersistenUser(user, person, userId);

                return this.userMapper.toUserRes(userRepresentation, userId);
            } else if (KeycloakResponseStatus.EXIST.getCode().equals(status)) {
                log.warn("{} already exists", user);
                throw new BusinessException(BusinessExceptionReason.USER_ALREADY_EXISTS_KEYCLOAK, user.getUsername());
            } else {
                log.warn("Error creating user" + user);
                throw new BusinessException(BusinessExceptionReason.CREATED_KEYCLOAK_USER_FAILED, user.getUsername());
            }
        } catch (BusinessException ex) {
            if (StringUtils.isNotBlank(userIdCode)) {
                log.info("Deleting user: {}", userIdCode);
                this.deleteUserById(userIdCode);
            }

            throw new BusinessException(BusinessExceptionReason.CREATED_KEYCLOAK_USER_FAILED, user.getUsername());
        } catch (Exception e) {
            log.error("Error creating user: {}", e.getMessage());

            if (StringUtils.isNotBlank(userIdCode)) {
                log.info("Deleting user: {}", userIdCode);
                this.deleteUserById(userIdCode);
            }

            throw new BusinessException(BusinessExceptionReason.CREATED_KEYCLOAK_USER_FAILED, user.getUsername());
        } finally {
            if (response != null) {
                log.info("Closing response {}", response);
                response.close();
            }
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public List<UserRes> findAllUsers() {
        List<UserRepresentation> userRepresentations = getUsersResource().list();
        return userRepresentations.stream()
                .map(userRepresentation -> this.userMapper.toUserRes(userRepresentation, userRepresentation.getId()))
                .collect(Collectors.toList());
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRes findUserById(String id) {
        UserRepresentation userRepresentation = getUserRepresentationById(id);
        return this.userMapper.toUserRes(userRepresentation, id);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void updateUserById(String id, UpdateUserReq user) {
        Person person = personRepository.findById(user.getPersonId())
                .orElseThrow(() -> {
                    log.error("Person not found for id: {}", user.getPersonId());
                    return new BusinessException(BusinessExceptionReason.PERSON_NOT_FOUND);
                });

        this.userRepository.findById(id).ifPresent(userEntity -> {
            log.info("Before update: {}", userEntity);
            userEntity.setPerson(person);
            log.info("After update: {}", userEntity);
        });

        UserRepresentation userRepresentation = getUserRepresentationById(id);
        try {
            userRepresentation.setEmail(user.getEmail());
            userRepresentation.setFirstName(user.getFirstName());
            userRepresentation.setLastName(user.getLastName());

            keycloakUtil.getRealmResource().users().get(id).update(userRepresentation);
        } catch (Exception e) {
            log.error("Error updating user: {}", e.getMessage());
            throw new BusinessException(BusinessExceptionReason.USER_UPDATE_FAILED);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    @Transactional
    public void deleteUserById(String id) {
        this.userRepository.findById(id).ifPresent(user -> this.userRepository.deleteById(id));

        UserRepresentation userRepresentation = getUserRepresentationById(id);
        try {
            getUsersResource().delete(userRepresentation.getId());
        } catch (Exception e) {
            throw new BusinessException(BusinessExceptionReason.USER_KEYCLOAK_DELETE_FAILED);
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public UserRes findUserByUserName(String userName) {
        Collection<UserRepresentation> userRepresentations = getUsersResource().list();
        UserRepresentation userRepresentation = userRepresentations.stream()
                .filter(user -> user.getUsername().equals(userName))
                .findAny()
                .orElse(null);
        if (Objects.nonNull(userRepresentation)) {
            return this.userMapper.toUserRes(userRepresentation, userRepresentation.getId());
        } else {
            throw new BusinessException(BusinessExceptionReason.USER_NOT_FOUND_KEYCLOAK);
        }
    }

    @Override
    public GenerateUsernameRes generateUsername(GenerateUsernameReq req) {
        String firstPart = StringUtils.stripAccents(req.getFirstName().substring(0, 1).toLowerCase());
        String lastName = req.getLastName().toLowerCase();

        if (lastName.contains(" ")) {
            lastName = lastName.substring(0, lastName.indexOf(" "));
        }

        String username = firstPart + lastName;
        int repeatedUsernames = userRepository.countByUsername(username);

        if (repeatedUsernames > 0) {
            int nextNumber = repeatedUsernames + 1;
            username = username + nextNumber;
        }

        return new GenerateUsernameRes(username);
    }

    @Override
    public boolean existsByEmail(String email) {
        Collection<UserRepresentation> userRepresentations = getUsersResource().list();
        UserRepresentation userRepresentation = userRepresentations.stream()
                .filter(user -> user.getEmail().equals(email))
                .findAny()
                .orElse(null);
        return Objects.nonNull(userRepresentation);
    }


    /**
     * Get User Representation By ID
     *
     * @param id id
     * @return UserRepresentation
     */
    public UserRepresentation getUserRepresentationById(String id) {
        try {
            RealmResource realmResource = keycloakUtil.getRealmResource();
            return realmResource.users().get(id).toRepresentation();
        } catch (Exception e) {
            log.error("getUserRepresentationById Not Found Id {}", id);
            throw new BusinessException(BusinessExceptionReason.USER_NOT_FOUND_KEYCLOAK);
        }
    }

    private UsersResource getUsersResource() {
        return keycloakUtil.getRealmResource().users();
    }

    private void createPersistenUser(CreateUserReq createUserReq, Person person, String userId) {
        User user = User.builder()
                .code(userId)
                .username(createUserReq.getUsername())
                .build();
        Preferences preferences = preferencesService.getDefaultPreferences();
        preferences.setUser(user);
        user.setPreferences(preferences);
        user.setPerson(person);

        userRepository.save(user);
    }
}
