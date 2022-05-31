package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.Material;
import org.crsoft.cartonplast.vo.res.MaterialRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@Mapper(componentModel = "spring", uses = {
        TypeMaterialMapper.class,
})
public interface MaterialMapper {

    MaterialRes materialToMaterialRes(Material material);

    Collection<MaterialRes> materialCollectionToMaterialResCollection(Collection<Material> material);

}
