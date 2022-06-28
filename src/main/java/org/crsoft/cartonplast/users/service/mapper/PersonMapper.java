package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.Person;
import org.crsoft.cartonplast.users.model.User;
import org.crsoft.cartonplast.users.vo.res.PersonRes;
import org.crsoft.cartonplast.users.vo.res.ProfileRes;
import org.crsoft.cartonplast.users.vo.res.ShortPersonRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Mapper(componentModel = "spring", uses = {
        EthnicMapper.class,
        GenderMapper.class,
        GroupMapper.class
})
public interface PersonMapper {

    PersonRes toPersonRes(Person person);

    @WithoutAuditField
    Person toPerson(PersonRes personRes);

    @Mapping(target = "person", source = "user.person")
    @Mapping(target = "username", source = "user.username")
    ProfileRes toProfileRes(User user);

    ShortPersonRes toShortPersonRes(Person person);

    List<PersonRes> toPersonResList(List<Person> persons);

    List<ShortPersonRes> toShortPersonResList(List<Person> persons);
}
