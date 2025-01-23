package com.rober.service;

import com.rober.dto.ClientDTO;
import com.rober.model.Client;

public interface IClientService {

    ClientDTO save(ClientDTO clientDTO);

    ClientDTO update(String idClient, ClientDTO clientDTO);

    String delete(String idClient);

}
