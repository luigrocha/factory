package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.crsoft.cartonplast.design.vo.res.HomoPolymerRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring")
public interface HomoPolymerMapper {

    HomoPolymerRes homoPolymerToHomoPolymerRes(HomoPolymer homoPolymer);

    @WithoutAuditField
    HomoPolymer homoPolymerResToHomoPolymer(HomoPolymerRes homoPolymerRes);

    Collection<HomoPolymerRes> homoPolymersToHomoPolymersRes(Collection<HomoPolymer> homoPolymers);
}
