package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Machine;
import org.crsoft.cartonplast.design.vo.req.MachineReq;
import org.crsoft.cartonplast.design.vo.res.MachineRes;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface MachineMapper {

    MachineRes toMachineRes(Machine machine);

    @WithoutAuditField
    Machine toMachine(MachineRes machineRes);

    MachineReq toMachineReq(Machine machine);

    @WithoutAuditField
    Machine toMachine(MachineReq machineReq);

    List<MachineRes> toMachineResList(List<Machine> machines);
}
