package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Cyrel;
import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Mapper(componentModel = "spring", uses = {
        DieMapper.class,
        PrinterMapper.class,
        ColorBMapper.class,
        CyrelColorMapper.class
})
public interface CyrelMapper {

    @Mapping(target = "printer", source = "printer.name")
    CyrelRes cyrelToCyrelRes(Cyrel cyrel);

    @WithoutAuditField
    @Mapping(target = "printer", ignore = true)
    @Mapping(target = "cyrelColors", ignore = true)
    @Mapping(target = "mbLeaf", ignore = true)
    @Mapping(target = "die", ignore = true)
    Cyrel cyrelResToCyrel(CyrelRes cyrelRes);

    List<CyrelRes> cyrelsToCyrelsRes(List<Cyrel> cyrels);
}
