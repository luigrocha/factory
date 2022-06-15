package org.crsoft.cartonplast.design.service;

import org.crsoft.cartonplast.design.vo.req.DieProductReq;
import org.crsoft.cartonplast.design.vo.res.DieProductRes;

import java.util.List;

/**
 * @author lpillaga on 12/06/2022
 */
public interface IDieProductService {

    List<DieProductRes> findAllValidDieProducts();

    List<DieProductRes> findAllAvailableDieProducts();

    DieProductRes save(DieProductReq dieProductReq);

    DieProductRes update(Integer id, DieProductReq dieProductReq);

    boolean delete(Integer id);
}
