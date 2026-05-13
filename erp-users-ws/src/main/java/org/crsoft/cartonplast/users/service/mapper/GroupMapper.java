package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.Group;
import org.crsoft.cartonplast.users.vo.req.GroupReq;
import org.crsoft.cartonplast.users.vo.res.GroupRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Mapper(componentModel = "spring", uses = {
        FirstDigitMapper.class,
        SecondDigitMapper.class,
        DivisionMapper.class
})
public interface GroupMapper {

    GroupRes toGroupRes(Group group);

    @WithoutAuditField
    @Mapping(target = "division.id", source = "division")
    @Mapping(target = "firstDigit.id", source = "firstDigit")
    @Mapping(target = "secondDigit.id", source = "secondDigit")
    Group toGroup(GroupReq groupReq);

    @WithoutAuditField
    Group toGroup(GroupRes groupRes);

    List<GroupRes> toGroupResList(List<Group> groups);
}
