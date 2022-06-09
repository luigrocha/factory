package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.design.repository.MachineRepository;
import org.crsoft.cartonplast.design.service.IMachineService;
import org.crsoft.cartonplast.design.service.mapper.MachineMapper;
import org.crsoft.cartonplast.design.vo.req.MachineReq;
import org.crsoft.cartonplast.design.vo.res.MachineRes;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class MachineService implements IMachineService {

    private final MachineRepository machineRepository;
    private final MachineMapper machineMapper;

    @Override
    public List<MachineRes> findAllValidMachines() {
        return null;
    }

    @Override
    public MachineRes save(MachineReq machineReq) {
        return null;
    }

    @Override
    public MachineRes update(Integer id, MachineReq machineReq) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }
}
