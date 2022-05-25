package org.crsoft.cartonplast.client.service.impl;

import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.client.model.ClientCategory;
import org.crsoft.cartonplast.client.repository.ClientCategoryRepository;
import org.crsoft.cartonplast.client.repository.ClientRepository;
import org.crsoft.cartonplast.client.service.IClientService;
import org.crsoft.cartonplast.client.service.mapper.ClientMapper;
import org.crsoft.cartonplast.client.vo.req.CreateClientReq;
import org.crsoft.cartonplast.common.client.MinioClient;
import org.crsoft.cartonplast.common.constant.GlobalConstant;
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
public class ClientService implements IClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;
    private final ClientCategoryRepository clientCategoryRepository;
    private final MinioClient minioClient;

    @Value("${minio.images.bucket-name}")
    private String imagesBucketName;

    public ClientService(
            ClientMapper clientMapper,
            ClientRepository clientRepository,
            ClientCategoryRepository clientCategoryRepository,
            MinioClient minioClient) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
        this.clientCategoryRepository = clientCategoryRepository;
        this.minioClient = minioClient;
    }

    @Override
    public List<ClientRes> findAllValidClients() {
        return this.clientMapper.clientsToClientsRes(
                this.clientRepository.findAllValidClients()
        );
    }

    @Override
    public ClientRes saveClient(CreateClientReq createClientReq) {
        if (this.clientRepository.existsById(createClientReq.getId())) {
            throw new ConflictException("El cliente ya se encuentra registrado");
        }

        ClientCategory clientCategory = null;
        if (createClientReq.getCategoryId() != null) {
            clientCategory = this.clientCategoryRepository
                    .findById(createClientReq.getCategoryId())
                    .orElse(null);
        }

        String logoUrl = null;
        String logoName = null;
        if (createClientReq.getLogo() != null) {
            UploadFileReq uploadFileReq = UploadFileReq.builder()
                    .bucketName(this.imagesBucketName)
                    .directory(GlobalConstant.CLIENTS_IMAGES_DIRECTORY)
                    .file(createClientReq.getLogo())
                    .build();
            UploadFileRes uploadFileRes = minioClient.uploadFile(uploadFileReq);
            logoUrl = uploadFileRes.getFileUrl();
            logoName = uploadFileRes.getFileName();
        }

        Client client = Client.builder()
                .id(createClientReq.getId())
                .name(createClientReq.getName())
                .imageUrl(logoUrl)
                .imageName(logoName)
                .category(clientCategory)
                .build();

        return this.clientMapper.clientToClientRes(
                this.clientRepository.save(client)
        );
    }

    @Override
    @Transactional
    public boolean deleteClient(String clientId) {
        return this.clientRepository.findById(clientId)
                .map(client -> {
                    client.setValidTo(LocalDateTime.now());
                    return true;
                }).orElse(false);
    }
}
