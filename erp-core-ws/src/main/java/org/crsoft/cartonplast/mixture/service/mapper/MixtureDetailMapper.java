package org.crsoft.cartonplast.mixture.service.mapper;

import org.crsoft.cartonplast.celler.service.mapper.MaterialMapper;
import org.crsoft.cartonplast.common.annotation.WithoutAuditField;
import org.crsoft.cartonplast.mixture.model.Mixture;
import org.crsoft.cartonplast.mixture.model.MixtureDetail;
import org.crsoft.cartonplast.vo.req.MixtureDetailReq;
import org.crsoft.cartonplast.vo.res.MixtureDetailRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.Collection;

/**
 * @author jyepez on 3/7/2022
 */
@Mapper(componentModel = "spring", uses = {
        MixtureMapper.class,
        MaterialMapper.class
})
public interface MixtureDetailMapper {

    @WithoutAuditField
    @Mapping(target = "mixture.id", source = "mixture")
    @Mapping(target = "material.id", source = "material")
    MixtureDetail mixtureDetailResToMixtureDetail(MixtureDetailReq mixtureDetailReq);

    @WithoutAuditField
    Collection<MixtureDetail> mixtureDetailResCollectionToMixtureDetailCollection(Collection<MixtureDetailReq> mixtureDetailReq);

    MixtureDetailRes mixtureDetailToMixtureDetailRes(MixtureDetail mixtureDetail);

    Collection<MixtureDetailRes> mixtureDetailCollectionToMixtureDetailRes(Collection<MixtureDetail> mixtureDetail);

}
