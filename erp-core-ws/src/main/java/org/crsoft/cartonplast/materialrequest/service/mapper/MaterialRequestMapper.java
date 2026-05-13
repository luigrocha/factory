package org.crsoft.cartonplast.materialrequest.service.mapper;

import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.crsoft.cartonplast.vo.req.MaterialRequestReq;
import org.crsoft.cartonplast.vo.res.MaterialRequestRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author lpillaga on 15/07/2022
 */
@Mapper(componentModel = "spring")
public interface MaterialRequestMapper {

    MaterialRequestRes toMaterialRequestRes(MaterialRequest materialRequest);

    @Mapping(target = "status.id", source = "status")
    @Mapping(target = "turn.id", source = "turn")
    MaterialRequest toMaterialRequest(MaterialRequestReq materialRequestReq);
}
