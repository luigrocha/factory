package org.crsoft.cartonplast.client.service.impl;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.client.model.ClientCategory;
import org.crsoft.cartonplast.client.repository.ClientCategoryRepository;
import org.crsoft.cartonplast.client.repository.ClientRepository;
import org.crsoft.cartonplast.client.service.IClientService;
import org.crsoft.cartonplast.client.service.mapper.ClientMapper;
import org.crsoft.cartonplast.common.exception.BusinessException;
import org.crsoft.cartonplast.common.exception.BusinessExceptionReason;
import org.crsoft.cartonplast.vo.req.CreateClientReq;
import org.crsoft.cartonplast.common.client.MinioClient;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
import org.crsoft.cartonplast.common.constant.LogMessageConstant;
import org.crsoft.cartonplast.common.exception.ConflictException;
import org.crsoft.cartonplast.vo.req.UploadFileReq;
import org.crsoft.cartonplast.vo.res.ClientRes;
import org.crsoft.cartonplast.vo.res.UploadFileRes;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Service
@RequiredArgsConstructor
@Slf4j
public class ClientService implements IClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final ClientCategoryRepository clientCategoryRepository;
    private final MinioClient minioClient;

    @Value("${minio.images.bucket-name}")
    private String imagesBucketName;

    @Override
    public List<ClientRes> findAllValidClients() {
        return this.clientMapper.clientsToClientsRes(
                this.clientRepository.findAllValidClients()
        );
    }

    @Override
    public ClientRes saveClient(CreateClientReq createClientReq) {
        boolean alreadyExist = this.clientRepository.existsByCodeAndIsNotDeleted(createClientReq.getCode());
        if (alreadyExist) {
            log.error(LogMessageConstant.ERROR_DUPLICATE_RECORD_MESSAGE + createClientReq);
            throw new ConflictException("Ya existe un cliente con el código: " + createClientReq.getCode());
        }

        ClientCategory clientCategory = null;
        if (createClientReq.getCategoryId() != null) {
            clientCategory = this.clientCategoryRepository
                    .findById(createClientReq.getCategoryId())
                    .orElse(null);
        }

        String logoName = null;
        if (createClientReq.getLogo() != null) {
            UploadFileReq uploadFileReq = UploadFileReq.builder()
                    .name(createClientReq.getCode())
                    .bucketName(this.imagesBucketName)
                    .directory(GlobalConstant.CLIENTS_IMAGES_DIRECTORY)
                    .file(createClientReq.getLogo())
                    .build();
            UploadFileRes uploadFileRes = minioClient.uploadFile(uploadFileReq);
            logoName = uploadFileRes.getFileName();
        }

        Client client = Client.builder()
                .code(createClientReq.getCode())
                .name(createClientReq.getName())
                .imageName(logoName)
                .category(clientCategory)
                .build();

        return this.clientMapper.clientToClientRes(
                this.clientRepository.save(client)
        );
    }

    @Override
    @Transactional
    public boolean deleteClient(Integer clientId) {
        return this.clientRepository.findById(clientId)
                .map(client -> {
                    client.setValidTo(LocalDateTime.now());
                    return true;
                }).orElse(false);
    }

    @Override
    public Client findClientById(Integer clientId) {
        return this.clientRepository.findById(clientId)
                .orElseThrow(() -> {
                    log.error("Client not found with id: {}", clientId);
                    return new BusinessException(BusinessExceptionReason.CLIENT_NOT_FOUND, clientId);
                });
    }
}
