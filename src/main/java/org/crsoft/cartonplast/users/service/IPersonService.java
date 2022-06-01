package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.res.PersonRes;
import org.crsoft.cartonplast.users.vo.res.ProfileRes;
import org.crsoft.cartonplast.users.vo.res.UserImageRes;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
public interface IPersonService {

    List<PersonRes> findAllValidPersons();

    ProfileRes findProfileByUsername(String username);

    UserImageRes getImage(String username);

    boolean delete(Integer id);
}
