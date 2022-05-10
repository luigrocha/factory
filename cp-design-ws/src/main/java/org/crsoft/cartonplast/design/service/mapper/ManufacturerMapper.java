package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Manufacturer;
import org.crsoft.cartonplast.design.vo.res.ManufacturerRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 08/05/2022
 */
@Mapper(componentModel = "spring")
public interface ManufacturerMapper {

    ManufacturerRes manufacturerToManufacturerRes(Manufacturer manufacturer);

    @WithoutAuditField
    Manufacturer manufacturerResToManufacturer(ManufacturerRes manufacturerRes);

    List<ManufacturerRes> manufacturersToManufacturersRes(List<Manufacturer> manufacturers);
}
