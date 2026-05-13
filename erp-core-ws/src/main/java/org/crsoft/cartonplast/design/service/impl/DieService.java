package org.crsoft.cartonplast.design.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.common.constant.CatalogStatusConstant;
import org.crsoft.cartonplast.common.constant.LogMessageConstant;
import org.crsoft.cartonplast.common.exception.NotExistException;
import org.crsoft.cartonplast.catalog.model.CatalogStatus;
import org.crsoft.cartonplast.catalog.service.impl.CatalogStatusService;
import org.crsoft.cartonplast.design.model.*;
import org.crsoft.cartonplast.design.model.pk.DieMachineId;
import org.crsoft.cartonplast.design.repository.DieProductRepository;
import org.crsoft.cartonplast.design.repository.DieRepository;
import org.crsoft.cartonplast.design.repository.MachineRepository;
import org.crsoft.cartonplast.design.service.IDieService;
import org.crsoft.cartonplast.design.repository.ManufacturerRepository;
import org.crsoft.cartonplast.design.service.mapper.DieMapper;
import org.crsoft.cartonplast.vo.req.DieReq;
import org.crsoft.cartonplast.vo.res.DieRes;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class DieService implements IDieService {

    private final DieRepository dieRepository;
    private final ManufacturerRepository manufacturerRepository;
    private final MachineRepository machineRepository;
    private final DieProductRepository dieProductRepository;
    private final CatalogStatusService catalogStatusService;
    private final DieMapper dieMapper;

    @Override
    public List<DieRes> findAllValidDies() {
        return this.dieMapper.diesToDiesResList(this.dieRepository.findAllValidDies());
    }

    @Override
    @Transactional
    public DieRes save(DieReq dieReq) {
        Optional<CatalogStatus> catalogStatusOptional = catalogStatusService.findByTypeAndIsDefault(CatalogStatusConstant.DIE_STATUS_CODE);

        if (catalogStatusOptional.isEmpty()) {
            log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieReq);
            throw new NotExistException("No existe un estado por defecto para troqueles");
        }

        DieProduct dieProduct = this.dieProductRepository
                .findById(dieReq.getDieProductId())
                .orElseThrow(() -> {
                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieReq);
                    throw new NotExistException("El producto troquelado no existe");
                });

        Manufacturer manufacturer = this.manufacturerRepository
                .findById(dieReq.getManufacturerId())
                .orElseThrow(() -> {
                    log.error(LogMessageConstant.ERROR_FIND_RECORD_MESSAGE + dieReq);
                    throw new NotExistException("El fabricante no existe");
                });

        Die die = this.dieMapper.dieReqToDie(dieReq);
        die.setDieProduct(dieProduct);
        die.setManufacturer(manufacturer);
        die.setStatus(catalogStatusOptional.get());

        List<Machine> machines = machineRepository.findAllById(dieReq.getMachines());

        List<DieMachine> dieMachines = machines
                .stream()
                .map(machine -> {
                    DieMachine dieMachine = new DieMachine();
                    dieMachine.setDieMachineId(new DieMachineId(machine.getId(), null));
                    dieMachine.setDie(die);
                    dieMachine.setMachine(machine);
                    return dieMachine;
                }).collect(Collectors.toList());
        die.setDieMachines(dieMachines);

        return this.dieMapper.dieToDieRes(this.dieRepository.save(die));
    }

    @Override
    public DieRes update(Integer id, DieReq dieReq) {
        return null;
    }

    @Override
    public boolean delete(Integer id) {
        return false;
    }

    @Override
    public Collection<DieRes> findByDieProduct(Integer code) {
        return this.dieMapper.diesToDiesResList(this.dieRepository.findByDieProduct(code));
    }
}
