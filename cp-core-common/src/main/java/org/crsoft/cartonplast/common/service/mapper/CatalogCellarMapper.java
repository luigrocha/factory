package org.crsoft.cartonplast.common.service.mapper;

import org.crsoft.cartonplast.common.model.CatalogCellar;
import org.crsoft.cartonplast.vo.res.CatalogCellarRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 27/5/2022
 */
@Mapper(componentModel = "spring", uses = {
        TypeCellarMapper.class,
})
public interface CatalogCellarMapper {

    CatalogCellarRes catalogCellarToCatalogCellarRes(CatalogCellar catalogCellar);

    Collection<CatalogCellarRes> catalogCellarCollectionToCatalogCellarResCollection(Collection<CatalogCellar> catalogCellar);

}
