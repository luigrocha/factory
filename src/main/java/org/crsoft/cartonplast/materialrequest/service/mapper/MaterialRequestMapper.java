package org.crsoft.cartonplast.materialrequest.service.mapper;

import org.crsoft.cartonplast.materialrequest.model.MaterialRequest;
import org.crsoft.cartonplast.vo.res.MaterialRequestRes;
import org.mapstruct.Mapper;

/**
 * @author lpillaga on 15/07/2022
 */
@Mapper(componentModel = "spring")
public interface MaterialRequestMapper {

    MaterialRequestRes toMaterialRequestRes(MaterialRequest materialRequest);
}
