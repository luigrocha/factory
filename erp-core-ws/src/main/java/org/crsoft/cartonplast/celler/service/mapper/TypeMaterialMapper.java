package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.TypeMaterial;
import org.crsoft.cartonplast.vo.res.TypeMaterialRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@Mapper(componentModel = "spring")
public interface TypeMaterialMapper {

    TypeMaterialRes typeMaterialToTypeMaterialRes(TypeMaterial typeMaterial);

    Collection<TypeMaterialRes> typeMaterialCollectionToTypeMaterialResCollection(Collection<TypeMaterial> typeMaterial);

}
