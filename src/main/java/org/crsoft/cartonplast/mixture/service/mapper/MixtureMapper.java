package org.crsoft.cartonplast.mixture.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.vo.req.MixtureReq;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

/**
 * @author jyepez on 3/7/2022
 */
@Mapper(componentModel = "spring")
public interface MixtureMapper {

    @WithoutAuditField
    @Mapping(target = "order.id", source = "order")
    Mixture mixtureResToMixture(MixtureReq mixtureReq);

}
