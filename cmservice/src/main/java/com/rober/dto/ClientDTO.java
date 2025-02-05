package com.rober.dto;

import com.rober.model.Client;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import com.rober.model.Client.Gender;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ClientDTO {

    private Integer idClient;
    private String nombre;
    private String apellido;
    private Gender genero;
    private String direccion;
    private String telefono;
    private Date fecRegistro;

    public ClientDTO(Integer idClient) {
        this.idClient = idClient;
    }
}
