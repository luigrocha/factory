package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.Celler;
import org.crsoft.cartonplast.vo.res.CellerRes;
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
public interface CellerMapper {

    CellerRes cellerToCellerRes(Celler celler);

    Collection<CellerRes> cellerCollectionToCellerResCollection(Collection<Celler> cellers);

}
