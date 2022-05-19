package org.crsoft.cartonplast.client.service.impl;

import org.crsoft.cartonplast.client.repository.ClientRepository;
import org.crsoft.cartonplast.client.service.IClientService;
import org.crsoft.cartonplast.client.service.mapper.ClientMapper;
import org.crsoft.cartonplast.vo.res.ClientRes;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Service
public class ClientService implements IClientService {

    private final ClientMapper clientMapper;
    private final ClientRepository clientRepository;

    public ClientService(
            ClientMapper clientMapper,
            ClientRepository clientRepository) {
        this.clientMapper = clientMapper;
        this.clientRepository = clientRepository;
    }

    @Override
    public List<ClientRes> findAllValidClients() {
        return this.clientMapper.clientsToClientsRes(
                this.clientRepository.findAllValidClients()
        );
    }

    @Override
    public void saveClient(ClientRes client) {

    }
}
