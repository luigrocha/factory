package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.ConflictException;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.*;
import org.crsoft.cartonplast.users.repository.DivisionRepository;
import org.crsoft.cartonplast.users.repository.FirstDigitRepository;
import org.crsoft.cartonplast.users.repository.GroupRepository;
import org.crsoft.cartonplast.users.repository.SecondDigitRepository;
import org.crsoft.cartonplast.users.service.IGroupService;
import org.crsoft.cartonplast.users.service.mapper.GroupMapper;
import org.crsoft.cartonplast.users.vo.req.GroupReq;
import org.crsoft.cartonplast.users.vo.res.GroupRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

/**
 * @author lpillaga on 31/05/2022
 */
@Service
@Slf4j
@RequiredArgsConstructor
public class GroupService implements IGroupService {

    private final GroupRepository groupRepository;
    private final FirstDigitRepository firstDigitRepository;
    private final SecondDigitRepository secondDigitRepository;
    private final DivisionRepository divisionRepository;
    private final GroupMapper groupMapper;

    @Override
    public List<GroupRes> findAllValidGroups() {
        return groupMapper.toGroupResList(groupRepository.findAllValidGroups());
    }

    @Override
    @Transactional
    public GroupRes save(GroupReq groupReq) {

        FirstDigit firstDigit = firstDigitRepository.findById(groupReq.getFirstDigit())
                .orElseThrow(() -> {
                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + groupReq.getFirstDigit());
                    return new NotExistException(MessageConstant.MESSAGE_NOT_FOUND + ": " + groupReq.getFirstDigit());
                });

        SecondDigit secondDigit = secondDigitRepository.findById(groupReq.getSecondDigit())
                .orElseThrow(() -> {
                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + groupReq.getSecondDigit());
                    return new NotExistException(MessageConstant.MESSAGE_NOT_FOUND + ": " + groupReq.getFirstDigit());
                });

        Division division = divisionRepository.findById(groupReq.getDivision())
                .orElseThrow(() -> {
                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + groupReq.getDivision());
                    return new NotExistException(MessageConstant.MESSAGE_NOT_FOUND + ": " + groupReq.getDivision());
                });

        String generatedId = generateId(firstDigit, secondDigit, division);

        boolean alreadyExists = groupRepository.existsById(generatedId);

        if (alreadyExists) {
            log.error(LogMessageConstant.ERROR_INSERT_MESSAGE + groupReq);
            throw new ConflictException(MessageConstant.MESSAGE_CONFLICT_INSERT);
        }

        Group group = groupMapper.toGroup(groupReq);
        group.setFirstDigit(firstDigit);
        group.setSecondDigit(secondDigit);
        group.setDivision(division);
        group = groupRepository.save(group);

        return groupMapper.toGroupRes(group);

    }

    @Override
    @Transactional
    public GroupRes update(String id, GroupReq groupReq) {
        Optional<Group> groupOptional = groupRepository.findById(id);

        if (groupOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + groupReq);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        Group group = groupOptional.get();
        group.setName(groupReq.getName());

        return groupMapper.toGroupRes(group);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return groupRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }

    private String generateId(FirstDigit firstDigit, SecondDigit secondDigit, Division division) {
        return firstDigit.getId() + secondDigit.getId() + division.getId();
    }
}
