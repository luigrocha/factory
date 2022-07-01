package org.crsoft.cartonplast.design.service.mapper;

import org.crsoft.cartonplast.common.util.MinioUrlFileUtil;
import org.crsoft.cartonplast.design.model.CyrelDocument;
import org.crsoft.cartonplast.vo.res.CyrelDocumentRes;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 20/06/2022
 */
@Mapper(componentModel = "spring", uses = {
        MinioUrlFileUtil.class
})
public interface CyrelDocumentMapper {

    @Mapping(target = "documentUrl", source = "name", qualifiedByName = "getDocumentUrl")
    CyrelDocumentRes toCyrelDocumentRes(CyrelDocument cyrelDocument);

    List<CyrelDocumentRes> toCyrelDocumentResList(List<CyrelDocument> cyrelDocumentList);
}
