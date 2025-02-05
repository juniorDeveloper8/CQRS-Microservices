package com.rober.service.impl;

import com.rober.dto.ClientEvent;
import com.rober.model.Client;
import com.rober.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.rober.service.IClientService;

@Service
public class ClientServiceImpl implements IClientService {

    @Autowired
    private ClientRepository repository;

    @Override
    public Client findByIdClient(Integer idClient) {
        return repository.findByIdClient(idClient);
    }

    @Override
    @KafkaListener(topics = "client-event-topic", groupId = "client-event-group")
    public void processClientsEvent(ClientEvent clientEvent) {
        try {
            if (clientEvent == null || clientEvent.getClient() == null) {
                System.err.println("Mensaje de evento o cliente nulo recibido");
                return;
            }

            Client client = clientEvent.getClient();
            String eventType = clientEvent.getEventType();

            System.out.println("Evento recibido: " + eventType);
            System.out.println("Cliente recibido: " + client);

            switch (eventType) {
                case "CreateCliente":
                    System.out.println("Guardando cliente: " + client);
                    repository.save(client);
                    break;

                case "UpdateCliente":
                    Client existingClients = repository.findById(client.getIdClient()).orElse(null);
                    if (existingClients != null) {
                        existingClients.setNombre(client.getNombre());
                        existingClients.setApellido(client.getApellido());
                        existingClients.setGenero(client.getGenero());
                        existingClients.setDireccion(client.getDireccion());
                        existingClients.setTelefono(client.getTelefono());
                        existingClients.setFecRegistro(client.getFecRegistro());
                        System.out.println("Actualizando cliente: " + existingClients);
                        repository.save(existingClients);
                    } else {
                        System.err.println("Cliente no encontrado para actualizar: " + client.getIdClient());
                    }
                    break;

                case "DeleteCliente":
                    repository.deleteById(client.getIdClient());
                    System.out.println("Cliente eliminado: " + client.getIdClient());
                    break;

                default:
                    System.err.println("Evento desconocido: " + eventType);
                    break;
            }
        } catch (Exception e) {
            System.err.println("Error procesando el evento: " + e.getMessage());
            e.printStackTrace();
        }
    }

}
