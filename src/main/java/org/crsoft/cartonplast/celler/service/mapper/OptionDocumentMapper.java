package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.OptionDocument;
import org.crsoft.cartonplast.vo.res.OptionDocumentRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 1/6/2022
 */
@Mapper(componentModel = "spring", uses = {
        DocumentMapper.class
})
public interface OptionDocumentMapper {

    OptionDocumentRes optionDocumentToOptionDocumentRes(OptionDocument optionDocument);

    Collection<OptionDocumentRes> optionDocumentCollectionToOptionDocumentResCollection(Collection<OptionDocument> optionDocuments);

}
