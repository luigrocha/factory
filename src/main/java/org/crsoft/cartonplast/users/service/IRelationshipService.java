package org.crsoft.cartonplast.users.service;

import org.crsoft.cartonplast.users.vo.req.RelationshipReq;
import org.crsoft.cartonplast.users.vo.res.RelationshipRes;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
public interface IRelationshipService {

    List<RelationshipRes> findAllValidRelationShips();

    RelationshipRes save(RelationshipReq relationshipReq);

    RelationshipRes update(String id, RelationshipReq relationshipReq);

    boolean delete(String id);
}
