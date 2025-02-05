package com.rober.service.impl;

import com.rober.dto.AccountDTO;
import com.rober.dto.AccountEvents;
import com.rober.dto.ClientDTO;
import com.rober.dto.ClientEvent;
import com.rober.exceptions.AccountException;
import com.rober.exceptions.ClientException;
import com.rober.mapper.AccountMapper;
import com.rober.mapper.ClientsMapper;
import com.rober.model.Account;
import com.rober.model.Client;
import com.rober.repository.AccountRepository;
import com.rober.repository.ClientRepository;
import com.rober.service.IClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Random;
import java.util.UUID;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private ClientsMapper clientsMapper;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public ClientDTO save(ClientDTO clientDTO) {
        try {

            Client savedClient = saveClient(clientDTO);

            AccountDTO savedAccountDTO = saveAccount(savedClient);

            sendKafkaEvents(clientDTO, savedAccountDTO);

            return clientsMapper.toDTO(savedClient);
        } catch (Exception e) {
            throw new ClientException("Error al guardar el cliente: " + e.getMessage(), e);
        }
    }
    //*****************************LOGICA DEL METODO GUARDAR**************************************//

    private Client saveClient(ClientDTO clientDTO) {

        Client client = new Client();
        client.setNombre(clientDTO.getNombre());
        client.setApellido(clientDTO.getApellido());
        client.setGenero(clientDTO.getGenero());
        client.setDireccion(clientDTO.getDireccion());
        client.setTelefono(clientDTO.getTelefono());
        client.setFecRegistro(clientDTO.getFecRegistro());

        return clientRepository.save(client);
    }

    private AccountDTO saveAccount(Client savedClient) {
        Random randomNumbers = new Random();
        Account account = new Account();
        account.setClient(savedClient);
        account.setSaldo(BigDecimal.ZERO);
        account.setDeuda(BigDecimal.ZERO);
        account.setFechaCorte(savedClient.getFecRegistro());
        account.setLimitCredit(new BigDecimal(1000 + randomNumbers.nextLong(9001)));
        account.setStatus(true);

        Account savedAccount = accountRepository.save(account);
        return accountMapper.toDTO(savedAccount);
    }

    private void sendKafkaEvents(ClientDTO clientDTO, AccountDTO savedAccountDTO) {
        kafkaTemplate.send("account-event-topic", new AccountEvents("CreateCuenta", savedAccountDTO));
        kafkaTemplate.send("client-event-topic", new ClientEvent("CreateCliente", clientDTO));
    }

    //*****************************FIN DE LOGICA **************************************//

    @Override
    public ClientDTO update(Integer idClient, ClientDTO clientDTO) {
        try {
            return clientRepository.findById(idClient)
                    .map(client -> {
                        client.setNombre(clientDTO.getNombre());
                        client.setApellido(clientDTO.getApellido());
                        client.setGenero(clientDTO.getGenero());
                        client.setDireccion(clientDTO.getDireccion());
                        client.setTelefono(clientDTO.getTelefono());
                        client.setFecRegistro(clientDTO.getFecRegistro());
                        ClientDTO savedClientDTO = clientsMapper.toDTO(clientRepository.save(client));
                        kafkaTemplate.send("client-event-topic", new ClientEvent("UpdateCliente", savedClientDTO));
                        return savedClientDTO;
                    }).orElseThrow(() -> new ClientException("Error con el id del cliente: " + idClient));
        } catch (Exception e) {
            throw new ClientException("Error al actualizar el cliente: " + e.getMessage());
        }
    }

}
