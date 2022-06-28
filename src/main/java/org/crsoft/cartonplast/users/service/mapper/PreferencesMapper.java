package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.Preferences;
import org.crsoft.cartonplast.users.vo.req.PreferencesReq;
import org.crsoft.cartonplast.users.vo.res.PreferencesRes;
import org.mapstruct.Mapper;

/**
 * @author lpillaga on 26/06/2022
 */
@Mapper(componentModel = "spring")
public interface PreferencesMapper {

    PreferencesRes preferencesToPreferencesRes(Preferences preferences);
}
