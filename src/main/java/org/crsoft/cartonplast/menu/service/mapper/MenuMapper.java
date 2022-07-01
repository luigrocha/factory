package org.crsoft.cartonplast.menu.service.mapper;

import org.crsoft.cartonplast.menu.model.Menu;
import org.crsoft.cartonplast.vo.res.MenuRes;

/**
 * @author jyepez on 19/5/2022
 */
// @Mapper(componentModel = "spring")
public interface MenuMapper {

    MenuRes menuToMenuRes(Menu menu);
}
