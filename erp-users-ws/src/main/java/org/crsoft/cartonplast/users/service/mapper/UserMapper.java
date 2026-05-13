package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.User;
import org.crsoft.cartonplast.users.util.KeycloakUtil;
import org.crsoft.cartonplast.users.vo.res.UserRes;
import org.keycloak.representations.idm.UserRepresentation;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 26/06/2022
 */
@Mapper(componentModel = "spring", uses = {
        KeycloakUtil.class,
        PersonMapper.class
})
public interface UserMapper {

    UserRes toUserRes(User user);

    @Mapping(source = "user.id", target = "id")
    @Mapping(source = "user.username", target = "username")
    @Mapping(source = "user.email", target = "email")
    @Mapping(source = "user.firstName", target = "firstName")
    @Mapping(source = "user.lastName", target = "lastName")
    @Mapping(source = "userId", target = "roles", qualifiedByName = "getRolesByUserId")
    UserRes toUserRes(UserRepresentation user, String userId);

    List<UserRes> toUserResList(List<User> users);
}
