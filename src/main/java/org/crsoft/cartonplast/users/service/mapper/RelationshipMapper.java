package org.crsoft.cartonplast.users.service.mapper;

import org.crsoft.cartonplast.users.model.Relationship;
import org.crsoft.cartonplast.users.vo.req.RelationshipReq;
import org.crsoft.cartonplast.users.vo.res.RelationshipRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 31/05/2022
 */
@Mapper(componentModel = "spring")
public interface RelationshipMapper {

    RelationshipRes toRelationshipRes(Relationship relationship);

    @WithoutAuditField
    Relationship toRelationship(RelationshipRes relationshipRes);

    @WithoutAuditField
    Relationship toRelationship(RelationshipReq relationshipReq);

    List<RelationshipRes> toRelationshipResList(List<Relationship> relationships);
}
