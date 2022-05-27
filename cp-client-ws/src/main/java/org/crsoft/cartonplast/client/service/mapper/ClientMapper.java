package org.crsoft.cartonplast.client.service.mapper;

import org.crsoft.cartonplast.client.model.Client;
import org.crsoft.cartonplast.vo.res.ClientRes;
import org.crsoft.cartonplast.client.vo.res.ClientShortRes;
import org.crsoft.cartonplast.common.service.mapper.WithoutAuditField;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
@Mapper(componentModel = "spring")
public interface ClientMapper {

    ClientRes clientToClientRes(Client client);

    ClientShortRes clientToClientShortRes(Client client);

    @WithoutAuditField
    @Mapping(target = "category", ignore = true)
    @Mapping(target = "imageUrl", ignore = true)
    @Mapping(target = "imageName", ignore = true)
    Client clientResToClient(ClientRes clientRes);

    List<ClientRes> clientsToClientsRes(List<Client> clients);
}
