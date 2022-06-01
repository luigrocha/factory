package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Document;
import org.crsoft.cartonplast.celler.repository.DocumentRepository;
import org.crsoft.cartonplast.celler.service.IDocumentService;
import org.crsoft.cartonplast.celler.service.mapper.DocumentMapper;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.DocumentRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 31/5/2022
 */
@Service
@Slf4j
public class DocumentService implements IDocumentService {

    private final DocumentRepository documentRepository;
    private final DocumentMapper documentMapper;

    public DocumentService(DocumentRepository documentRepository, DocumentMapper documentMapper) {
        this.documentRepository = documentRepository;
        this.documentMapper = documentMapper;
    }

    @Override
    public Collection<DocumentRes> findAllDocument() throws NotFoundException {
        Collection<Document> documents = this.documentRepository.findAllByValidToIsNullOrderByNameAsc();
        if (CollectionUtil.isNotEmpty(documents)) {
            return this.documentMapper.documentCollectionToDocumentResCollection(documents);
        } else {
            log.error("Error to findAllDocument");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
