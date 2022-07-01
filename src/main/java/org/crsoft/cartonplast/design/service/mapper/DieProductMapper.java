package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.DieProduct;
import org.crsoft.cartonplast.vo.req.DieProductReq;
import org.crsoft.cartonplast.vo.res.DieProductRes;
import org.crsoft.cartonplast.vo.res.DieProductShortRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 12/06/2022
 */
@Mapper(componentModel = "spring", uses = {
        CatalogStatusMapper.class
})
public interface DieProductMapper {

    DieProductRes toDieProductRes(DieProduct dieProduct);

    DieProductShortRes toDieProductShortRes(DieProduct dieProduct);

    @Mapping(target = "dies", ignore = true)
    @Mapping(target = "cyrels", ignore = true)
    @WithoutAuditField
    DieProduct toDieProduct(DieProductRes dieProductRes);

    @WithoutAuditField
    @Mapping(target = "dies", ignore = true)
    @Mapping(target = "cyrels", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    DieProduct toDieProduct(DieProductReq dieProductReq);

    List<DieProductRes> toDieProductResList(List<DieProduct> dieProducts);

    List<DieProductShortRes> toDieProductShortResList(List<DieProduct> dieProducts);
}
