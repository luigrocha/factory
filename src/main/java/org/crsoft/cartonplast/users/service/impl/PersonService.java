package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.User;
import org.crsoft.cartonplast.users.repository.PersonRepository;
import org.crsoft.cartonplast.users.repository.UserRepository;
import org.crsoft.cartonplast.users.service.IPersonService;
import org.crsoft.cartonplast.users.service.mapper.PersonMapper;
import org.crsoft.cartonplast.users.util.MinioImageUtil;
import org.crsoft.cartonplast.users.vo.res.PersonRes;
import org.crsoft.cartonplast.users.vo.res.ProfileRes;
import org.crsoft.cartonplast.users.vo.res.UserImageRes;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 31/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class PersonService implements IPersonService {

    private final PersonRepository personRepository;
    private final UserRepository userRepository;
    private final PersonMapper personMapper;
    private final UserService userService;

    private final MinioImageUtil minioImageUtil;

    @Override
    public List<PersonRes> findAllValidPersons() {
        return personMapper.toPersonResList(personRepository.findAllValidPersons());
    }

    @Override
    public ProfileRes findProfileByUsername(String username) {
        Optional<User> userOptional = userRepository.findByGivenUsername(username);
        if (userOptional.isEmpty()) {
            log.info(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + username);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        User user = userOptional.get();
        ProfileRes profileRes = personMapper.toProfileRes(user);
        profileRes.setRoles(userService.findUserByUserName(username).getRoles());
        return profileRes;
    }

    @Override
    public UserImageRes getImage(String username) {
        UserImageRes userImageRes = new UserImageRes();
        String imageName = userRepository.findImageNameByUsername(username);

        if (imageName == null) {
            return null;
        }

        String url = minioImageUtil.getImageUrl(imageName);
        userImageRes.setImageUrl(url);

        return userImageRes;
    }

    @Override
    public boolean delete(Integer id) {
        return personRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
