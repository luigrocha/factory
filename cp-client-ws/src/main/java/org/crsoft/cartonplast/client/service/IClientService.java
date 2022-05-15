package org.crsoft.cartonplast.client.service;

import org.crsoft.cartonplast.vo.res.ClientRes;

import java.util.List;

/**
 * @author lpillaga on 12/05/2022
 */
public interface IClientService {

    List<ClientRes> findAllValidClients();
}
