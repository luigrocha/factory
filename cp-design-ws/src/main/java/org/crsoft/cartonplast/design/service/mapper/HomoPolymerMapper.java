package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.HomoPolymer;
import org.crsoft.cartonplast.design.vo.res.HomoPolymerRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 11/05/2022
 */
@Mapper(componentModel = "spring")
public interface HomoPolymerMapper {

    HomoPolymerRes homoPolymerToHomoPolymerRes(HomoPolymer homoPolymer);

    HomoPolymer homoPolymerResToHomoPolymer(HomoPolymerRes homoPolymerRes);

    List<HomoPolymerRes> homoPolymersToHomoPolymersRes(List<HomoPolymer> homoPolymers);
}
