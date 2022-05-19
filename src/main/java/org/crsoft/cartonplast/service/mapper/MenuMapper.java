package org.crsoft.cartonplast.service.mapper;

import org.crsoft.cartonplast.model.Menu;
import org.crsoft.cartonplast.vo.res.MenuRes;
import org.mapstruct.Mapper;

/**
 * @author jyepez on 19/5/2022
 */
// @Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuRes menuToMenuRes(Menu menu);
}
