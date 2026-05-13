package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.vo.req.DieReq;
import org.crsoft.cartonplast.vo.res.DieRes;

import java.util.Collection;
import java.util.List;

public interface IDieService {
    List<DieRes> findAllValidDies();

    DieRes save(DieReq dieReq);

    DieRes update(Integer id, DieReq dieReq);

    boolean delete(Integer id);

    Collection<DieRes> findByDieProduct(Integer code);
}
