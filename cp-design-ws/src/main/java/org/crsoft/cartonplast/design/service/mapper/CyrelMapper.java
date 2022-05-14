package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.client.service.mapper.ClientMapper;
import org.crsoft.cartonplast.common.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Cyrel;
import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Mapper(componentModel = "spring", uses = {
        DieMapper.class,
        PrinterMapper.class,
        ColorBMapper.class,
        ClientMapper.class
})
public interface CyrelMapper {

    CyrelRes cyrelToCyrelRes(Cyrel cyrel);

    @WithoutAuditField
    Cyrel cyrelResToCyrel(CyrelRes cyrelRes);

    List<CyrelRes> cyrelsToCyrelsRes(List<Cyrel> cyrels);
}
