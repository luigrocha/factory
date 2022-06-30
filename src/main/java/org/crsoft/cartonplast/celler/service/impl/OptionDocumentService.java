package org.crsoft.cartonplast.celler.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.celler.model.Document;
import org.crsoft.cartonplast.celler.model.OptionDocument;
import org.crsoft.cartonplast.celler.repository.OptionDocumentRepository;
import org.crsoft.cartonplast.celler.service.IOptionDocumentService;
import org.crsoft.cartonplast.celler.service.mapper.OptionDocumentMapper;
import org.crsoft.cartonplast.common.exception.NotFoundException;
import org.crsoft.cartonplast.vo.res.OptionDocumentRes;
import org.keycloak.common.util.CollectionUtil;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

import static org.crsoft.cartonplast.common.constant.MessagesConstant.MESSAGE_NOT_FOUND;

/**
 * @author jyepez on 2/6/2022
 */
@Service
@Slf4j
public class OptionDocumentService implements IOptionDocumentService {

    private final OptionDocumentRepository optionDocumentRepository;
    private final OptionDocumentMapper optionDocumentMapper;
    private final DocumentService documentService;

    public OptionDocumentService(OptionDocumentRepository optionDocumentRepository, OptionDocumentMapper optionDocumentMapper, DocumentService documentService) {
        this.optionDocumentRepository = optionDocumentRepository;
        this.optionDocumentMapper = optionDocumentMapper;
        this.documentService = documentService;
    }

    @Override
    public Collection<OptionDocumentRes> findAllByDocumentCode(Integer id) throws NotFoundException {
        Document document = this.documentService.getDocumentById(id);
        Collection<OptionDocument> optionDocuments = this.optionDocumentRepository.findAllByDocumentAndValidToIsNullOrderByCreatedAtAsc(document);
        if (CollectionUtil.isNotEmpty(optionDocuments)) {
            return this.optionDocumentMapper.optionDocumentCollectionToOptionDocumentResCollection(optionDocuments);
        } else {
            log.error("Error to findAllByDocumentCode");
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }

    @Override
    public OptionDocument findByCode(Integer code) throws NotFoundException {
        Optional<OptionDocument> optionDocument = this.optionDocumentRepository.findByIdAndValidToIsNull(code);
        if (optionDocument.isPresent()) {
            return optionDocument.get();
        } else {
            log.error("Error to findByCode {}", code);
            throw new NotFoundException(MESSAGE_NOT_FOUND);
        }
    }
}
