package org.crsoft.cartonplast.celler.service.mapper;

import org.crsoft.cartonplast.celler.model.Document;
import org.crsoft.cartonplast.vo.res.DocumentRes;
import org.mapstruct.Mapper;

import java.util.Collection;

/**
 * @author jyepez on 31/5/2022
 */
@Mapper(componentModel = "spring")
public interface DocumentMapper {

    DocumentRes documentToDocumentRes(Document document);

    Collection<DocumentRes> documentCollectionToDocumentResCollection(Collection<Document> documents);

}
