package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.res.PersonRes;
import org.crsoft.cartonplast.users.vo.res.ProfileRes;
import org.crsoft.cartonplast.users.vo.res.ShortPersonRes;
import org.crsoft.cartonplast.users.vo.res.UserImageRes;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
public interface IPersonService {

    List<PersonRes> findAllValidPersons();

    List<ShortPersonRes> findByQuery(String query);

    ProfileRes findProfileByUsername(String username);

    UserImageRes getImage(String username);

    boolean delete(Integer id);

    ShortPersonRes findPersonByUserId(String userId);
}
