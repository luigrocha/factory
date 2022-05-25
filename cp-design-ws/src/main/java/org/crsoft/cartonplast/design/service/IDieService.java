package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.res.DieRes;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface IDieService {
    Page<DieRes> findAllValidDies(Pageable pageable, String query);
}
