package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.design.model.Die;
import org.crsoft.cartonplast.design.model.DieMachine;
import org.crsoft.cartonplast.design.vo.res.DieRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lpillaga on 02/05/2022
 */
@Mapper(componentModel = "spring", uses = {ManufacturerMapper.class})
public interface DieMapper {

    @Mapping(target = "manufacturer", source = "manufacturer.name")
    @Mapping(target = "machines", source = "dieMachines", qualifiedByName = "generateMachines")
    DieRes dieToDieRes(Die die);

    @Named("generateMachines")
    static List<String> generateMachines(List<DieMachine> dieMachines) {
        if (dieMachines.isEmpty()) {
            return Collections.emptyList();
        }
        return dieMachines.stream()
                .map(dieMachine -> dieMachine.getMachine().getName())
                .collect(Collectors.toList());
    }

    @Mapping(target = "manufacturer", ignore = true)
    @Mapping(target = "dieMachines", ignore = true)
    Die dieResToDie(DieRes dieRes);

    List<DieRes> diesToDiesResList(List<Die> dies);
}