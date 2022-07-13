package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.MachineCatalog;
import org.crsoft.cartonplast.vo.res.MachineCatalogRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 13/7/2022
 */
@Mapper(componentModel = "spring")
public interface MachineCatalogMapper {

    MachineCatalogRes toMachineCatalogRes(MachineCatalog machineCatalog);

    Collection<MachineCatalogRes> toMachineCatalogResCollection(Collection<MachineCatalog> machineCatalog);

}
