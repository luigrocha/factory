package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.DieProduct;
import org.crsoft.cartonplast.design.vo.req.DieProductReq;
import org.crsoft.cartonplast.design.vo.res.DieProductRes;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author lpillaga on 12/06/2022
 */
@Mapper(componentModel = "spring", uses = {
        CatalogStatusMapper.class
})
public interface DieProductMapper {

    DieProductRes toDieProductRes(DieProduct dieProduct);

    @WithoutAuditField
    DieProduct toDieProduct(DieProductRes dieProductRes);

    @WithoutAuditField
    DieProduct toDieProduct(DieProductReq dieProductReq);

    List<DieProductRes> toDieProductResList(List<DieProduct> dieProducts);
}
