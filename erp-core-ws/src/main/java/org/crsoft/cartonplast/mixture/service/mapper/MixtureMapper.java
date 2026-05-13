package org.crsoft.cartonplast.mixture.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.design.service.mapper.DieMapper;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.orders.service.mapper.OrderMapper;
import org.crsoft.cartonplast.vo.req.MixtureReq;
import org.crsoft.cartonplast.vo.res.MixtureRes;
import org.crsoft.cartonplast.vo.res.MixtureShortRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

/**
 * @author jyepez on 3/7/2022
 */
@Mapper(componentModel = "spring", uses = {
        DieMapper.class,
        OrderMapper.class
})
public interface MixtureMapper {

    @WithoutAuditField
    @Mapping(target = "die.id",  source = "die")
    @Mapping(target = "order.id", source = "order")
    Mixture mixtureResToMixture(MixtureReq mixtureReq);

    MixtureShortRes mixtureToMixtureShort(Mixture mixture);

    Collection<MixtureShortRes> mixtureCollectionToMixtureShort(Collection<Mixture> mixture);

    MixtureRes mixtureToMixtureRes(Mixture mixture);

    Collection<MixtureRes> mixtureCollectionToMixtureRes(Collection<Mixture> mixture);

}
