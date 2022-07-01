package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.constant.LogMessageConstant;
import org.crsoft.cartonplast.common.constant.MessagesConstant;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.design.model.Machine;
import org.crsoft.cartonplast.design.repository.MachineRepository;
import org.crsoft.cartonplast.design.service.IMachineService;
import org.crsoft.cartonplast.design.service.mapper.MachineMapper;
import org.crsoft.cartonplast.vo.req.MachineReq;
import org.crsoft.cartonplast.vo.res.MachineRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineService implements IMachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;

    @Override
    public List<MachineRes> findAllValidMachines() {
        return this.machineMapper.toMachineResList(this.machineRepository.findAllValidMachines());
    }

    @Override
    @Transactional
    public MachineRes save(MachineReq machineReq) {
        Machine machine = this.machineMapper.toMachine(machineReq);
        machine = this.machineRepository.save(machine);
        return this.machineMapper.toMachineRes(machine);
    }

    @Override
    @Transactional
    public MachineRes update(Integer id, MachineReq machineReq) {
        Optional<Machine> machineOptional = machineRepository.findById(id);

        if (machineOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + machineReq);
            throw new NotExistException(MessagesConstant.MESSAGE_NOT_FOUND);
        }

        Machine machine = machineOptional.get();
        machine.setName(machineReq.getName());
        machine.setHasDesb(machineReq.getHasDesb());

        return this.machineMapper.toMachineRes(machine);
    }

    @Override
    @Transactional
    public boolean delete(Integer id) {
        return machineRepository.findById(id)
                .map(machine -> {
                    machine.setValidTo(LocalDateTime.now());
                    return true;
                })
                .orElse(false);
    }
}
