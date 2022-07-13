package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.req.MachineReq;
import org.crsoft.cartonplast.vo.res.MachineCatalogRes;
import org.crsoft.cartonplast.vo.res.MachineRes;

import java.util.Collection;
import java.util.List;

/**
 * @author lpillaga on 09/06/2022
 */
public interface IMachineService {
    List<MachineRes> findAllValidMachines();

    MachineRes save(MachineReq machineReq);

    MachineRes update(Integer id, MachineReq machineReq);

    boolean delete(Integer id);

    Collection<MachineCatalogRes> findAllValidMachinesCatalog();
}
