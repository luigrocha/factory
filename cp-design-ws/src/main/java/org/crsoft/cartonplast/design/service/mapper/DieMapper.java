package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Die;
import org.crsoft.cartonplast.design.model.DieMachine;
import org.crsoft.cartonplast.design.vo.req.DieReq;
import org.crsoft.cartonplast.design.vo.res.DieRes;
import org.crsoft.cartonplast.design.vo.res.DieShortRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lpillaga on 02/05/2022
 */
@Mapper(componentModel = "spring", uses = {
        ManufacturerMapper.class,
        CatalogStatusMapper.class,
        DieProductMapper.class
})
public interface DieMapper {

    @Named("generateMachines")
    static List<String> generateMachines(List<DieMachine> dieMachines) {
        if (dieMachines.isEmpty()) {
            return Collections.emptyList();
        }
        return dieMachines.stream()
                .map(dieMachine -> dieMachine.getMachine().getName())
                .collect(Collectors.toList());
    }

    @Mapping(target = "manufacturer", source = "manufacturer.name")
    @Mapping(target = "machines", source = "dieMachines", qualifiedByName = "generateMachines")
    DieRes dieToDieRes(Die die);

    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dieMachines", ignore = true)
    @Mapping(target = "dieProduct", ignore = true)
    @Mapping(target = "documentName", ignore = true)
    @WithoutAuditField
    Die dieResToDie(DieRes dieRes);

    @Mapping(target = "status", ignore = true)
    @Mapping(target = "dieMachines", ignore = true)
    @Mapping(target = "dieProduct", ignore = true)
    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "documentName", ignore = true)
    @WithoutAuditField
    Die dieReqToDie(DieReq dieReq);

    DieShortRes dieToDieShortRes(Die die);

    List<DieRes> diesToDiesResList(List<Die> dies);
}