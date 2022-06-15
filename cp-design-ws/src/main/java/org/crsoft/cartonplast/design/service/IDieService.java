package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.req.DieReq;
import org.crsoft.cartonplast.design.vo.res.DieRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface IDieService {
    List<DieRes> findAllValidDies();

    DieRes save(DieReq dieReq);

    DieRes update(Integer id, DieReq dieReq);

    boolean delete(Integer id);
}
