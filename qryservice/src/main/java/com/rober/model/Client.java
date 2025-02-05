package com.rober.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "Client")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_client", unique = true, nullable = false)
    private Integer idClient;
    private String nombre;
    private String apellido;
    @Enumerated(EnumType.STRING)
    private Gender genero;
    private String direccion;
    private String telefono;
    private Date fecRegistro;
    public enum Gender{
        masculino,
        femenino
    }

}
