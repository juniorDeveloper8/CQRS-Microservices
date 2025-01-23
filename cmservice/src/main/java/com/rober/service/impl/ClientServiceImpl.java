package com.rober.service.impl;

import com.rober.dto.AccountDTO;
import com.rober.dto.AccountEvents;
import com.rober.dto.ClientDTO;
import com.rober.dto.ClientEvent;
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
            Random randomNumbers = new Random();

            Client client = new Client();
            client.setNombre(clientDTO.getNombre());
            client.setApellido(clientDTO.getApellido());
            client.setGenero(clientDTO.getGenero());
            client.setDireccion(clientDTO.getDireccion());
            client.setTelefono(clientDTO.getTelefono());
            client.setFecRegistro(clientDTO.getFecRegistro());

            Client savedClient = clientRepository.save(client);
            ClientDTO saveClientDTO = clientsMapper.toDTO(savedClient);

            // cuentas

            Account account = new Account();
            account.setClient(savedClient);
            account.setSaldo(new BigDecimal(0L));
            account.setDeuda(new BigDecimal(0L));
            account.setFechaCorte(savedClient.getFecRegistro());
            account.setLimitCredit(new BigDecimal(1000 + randomNumbers.nextLong(9001)));
            account.setStatus(true);

            Account savedAccount = accountRepository.save(account);
            AccountDTO savedAccountDTO = accountMapper.toDTO(savedAccount);

            kafkaTemplate.send("client-event-topic", new ClientEvent("CreateCliente", saveClientDTO));
            kafkaTemplate.send("account-event-topic", new AccountEvents("CreateCuenta", savedAccountDTO));

            return saveClientDTO;

        } catch (Exception e) {
            throw new ClientException("Error en Guardar Cliente metodo save: " + e.getMessage());
        }
    }

    @Override
    public ClientDTO update(String idClient, ClientDTO clientDTO) {
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

    @Override
    public String delete(String idClient) {
        try {
            if (clientRepository.existsById(idClient)) {
                clientRepository.deleteById(idClient);
                kafkaTemplate.send("client-event-topic", new ClientEvent("DeleteCliente", new ClientDTO(idClient)));
            }
            return "El cliende con id: " + idClient + " fue dado de baja correctamente! ";
        }catch (Exception e) {
            throw new ClientException("Error al eliminar el cliente: " + e.getMessage());
        }
    }
}
