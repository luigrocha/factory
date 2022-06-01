package org.crsoft.cartonplast.users.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.users.constant.LogMessageConstant;
import org.crsoft.cartonplast.users.constant.MessageConstant;
import org.crsoft.cartonplast.users.exception.ConflictException;
import org.crsoft.cartonplast.users.exception.NotExistException;
import org.crsoft.cartonplast.users.model.Relationship;
import org.crsoft.cartonplast.users.repository.RelationshipRepository;
import org.crsoft.cartonplast.users.service.IRelationshipService;
import org.crsoft.cartonplast.users.service.mapper.RelationshipMapper;
import org.crsoft.cartonplast.users.vo.req.RelationshipReq;
import org.crsoft.cartonplast.users.vo.res.RelationshipRes;
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
public class RelationshipService implements IRelationshipService {

    private final RelationshipRepository relationshipRepository;
    private final RelationshipMapper relationshipMapper;

    @Override
    public List<RelationshipRes> findAllValidRelationShips() {
        return relationshipMapper.toRelationshipResList(relationshipRepository.findAll());
    }

    @Override
    @Transactional
    public RelationshipRes save(RelationshipReq relationshipReq) {
        boolean alreadyExists = relationshipRepository.existsById(relationshipReq.getId());
        if (alreadyExists) {
            log.error(LogMessageConstant.ERROR_INSERT_MESSAGE + relationshipReq);
            throw new ConflictException(MessageConstant.MESSAGE_CONFLICT_INSERT);
        }

        Relationship relationship = relationshipMapper.toRelationship(relationshipReq);
        relationship = relationshipRepository.save(relationship);

        return relationshipMapper.toRelationshipRes(relationship);
    }

    @Override
    @Transactional
    public RelationshipRes update(String id, RelationshipReq relationshipReq) {
        Optional<Relationship> relationshipOptional = relationshipRepository.findById(id);
        if (relationshipOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + relationshipReq);
            throw new NotExistException(MessageConstant.MESSAGE_NOT_FOUND);
        }

        Relationship relationship = relationshipMapper.toRelationship(relationshipReq);
        relationship = relationshipRepository.save(relationship);

        return relationshipMapper.toRelationshipRes(relationship);
    }

    @Override
    @Transactional
    public boolean delete(String id) {
        return relationshipRepository.findById(id)
                .map(gender -> {
                    gender.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
