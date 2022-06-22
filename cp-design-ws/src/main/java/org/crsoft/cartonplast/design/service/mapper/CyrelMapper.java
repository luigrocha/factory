package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.service.mapper.CatalogStatusMapper;
import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.crsoft.cartonplast.design.model.Cyrel;
import org.crsoft.cartonplast.design.model.CyrelDieProduct;
import org.crsoft.cartonplast.design.vo.req.CyrelReq;
import org.crsoft.cartonplast.design.vo.res.CyrelRes;
import org.crsoft.cartonplast.design.vo.res.DieProductShortRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lpillaga on 12/05/2022
 */
@Mapper(componentModel = "spring", uses = {
        DieProductMapper.class,
        PrinterMapper.class,
        ColorBMapper.class,
        CyrelColorMapper.class,
        CatalogStatusMapper.class,
        CyrelDocumentMapper.class
})
public interface CyrelMapper {

    @Mapping(target = "printer", source = "printer.name")
    @Mapping(target = "dies", source = "dies", qualifiedByName = "toDieProductShortResList")
    CyrelRes cyrelToCyrelRes(Cyrel cyrel);

    @Named("toDieProductShortResList")
    static List<DieProductShortRes> toDieProductShortResList(List<CyrelDieProduct> cyrelDieProducts) {
        return cyrelDieProducts.stream()
                .map(cyrelDieProduct -> DieProductShortRes.builder()
                        .id(cyrelDieProduct.getDieProduct().getId())
                        .name(cyrelDieProduct.getDieProduct().getName())
                        .code(cyrelDieProduct.getDieProduct().getCode())
                        .build())
                .collect(Collectors.toList());
    }

    @WithoutAuditField
    @Mapping(target = "printer", ignore = true)
    @Mapping(target = "mbLeaf", ignore = true)
    @Mapping(target = "dies", ignore = true)
    @Mapping(target = "status", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "documents", ignore = true)
    Cyrel cyrelReqToCyrel(CyrelReq cyrelReq);

    List<CyrelRes> cyrelsToCyrelsRes(List<Cyrel> cyrels);
}
