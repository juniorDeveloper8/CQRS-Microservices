package com.rober.service;

import com.rober.dto.ClientEvent;
import com.rober.model.Client;

public interface IClientService {

    Client findByIdClient(String idClient);

    void processClientsEvent(ClientEvent clientEvent);

}