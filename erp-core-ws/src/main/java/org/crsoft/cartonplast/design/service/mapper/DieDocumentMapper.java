package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.util.MinioUrlFileUtil;
import org.crsoft.cartonplast.design.model.DieDocument;
import org.crsoft.cartonplast.vo.res.DieDocumentRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 20/06/2022
 */
@Mapper(componentModel = "spring", uses = {
        MinioUrlFileUtil.class
})
public interface DieDocumentMapper {

    @Mapping(target = "documentUrl", source = "name", qualifiedByName = "getDocumentUrl")
    DieDocumentRes toDieDocumentRes(DieDocument dieDocument);

    List<DieDocumentRes> toDieDocumentResList(List<DieDocument> dieDocuments);
}
