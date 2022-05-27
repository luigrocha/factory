package org.crsoft.cartonplast.common.service.mapper;

import org.crsoft.cartonplast.common.model.TypeCellar;
import org.crsoft.cartonplast.vo.res.TypeCellarRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@Mapper(componentModel = "spring")
public interface TypeCellarMapper {

    TypeCellarRes typeCellarToTypeCellarRes(TypeCellar typeCellar);

    Collection<TypeCellarRes> typeCellarCollectionToTypeCellarResCollection(Collection<TypeCellar> typeCellar);

}
