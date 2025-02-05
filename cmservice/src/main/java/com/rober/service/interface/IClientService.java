package com.rober.service;

import com.rober.dto.ClientDTO;
import com.rober.model.Client;

public interface IClientService {

    ClientDTO save(ClientDTO clientDTO);

    ClientDTO update(Integer idClient, ClientDTO clientDTO);

}
