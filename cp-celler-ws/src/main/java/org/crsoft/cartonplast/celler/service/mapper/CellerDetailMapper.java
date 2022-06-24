package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.CellerDetail;
import org.crsoft.cartonplast.vo.res.CellerDetailRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@Mapper(componentModel = "spring", uses = {
        MaterialMapper.class,
        LocationMapper.class,
        DocumentMapper.class
})
public interface CellerDetailMapper {

    CellerDetailRes cellerDetailToCellerDetailRes(CellerDetail celler);

    Collection<CellerDetailRes> cellerDetailCollectionToCellerDetailResCollection(Collection<CellerDetail> cellers);

}
