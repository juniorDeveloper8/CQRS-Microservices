package com.rober.mapper;

import com.rober.dto.ClientDTO;
import com.rober.model.Client;
import org.springframework.stereotype.Component;

@Component
public class ClientsMapper {
    public ClientDTO toDTO(Client client) {
        ClientDTO dto = new ClientDTO();
        dto.setIdClient(client.getIdClient());
        dto.setNombre(client.getNombre());
        dto.setApellido(client.getApellido());
        dto.setGenero(client.getGenero());
        dto.setDireccion(client.getDireccion());
        dto.setTelefono(client.getTelefono());
        dto.setFecRegistro(client.getFecRegistro());
        return dto;
    }

}
