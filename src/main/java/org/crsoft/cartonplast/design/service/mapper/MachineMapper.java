package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Machine;
import org.crsoft.cartonplast.vo.req.MachineReq;
import org.crsoft.cartonplast.vo.res.MachineRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {
        MachineCatalogMapper.class
})
public interface MachineMapper {

    MachineRes toMachineRes(Machine machine);

    @WithoutAuditField
    @Mapping(target = "dies", ignore = true)
    @Mapping(target = "dieMachines", ignore = true)
    Machine toMachine(MachineRes machineRes);

    MachineReq toMachineReq(Machine machine);

    @WithoutAuditField
    @Mapping(target = "dies", ignore = true)
    @Mapping(target = "dieMachines", ignore = true)
    @Mapping(target = "id", ignore = true)
    Machine toMachine(MachineReq machineReq);

    List<MachineRes> toMachineResList(List<Machine> machines);
}
