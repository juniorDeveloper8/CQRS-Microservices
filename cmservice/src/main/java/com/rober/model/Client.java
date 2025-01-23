package com.rober.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;
import lombok.*;

import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Client")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Client {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_client", unique = true, nullable = false, length = 200)
    private String idClient;
    private String nombre;
    private String apellido;

    @Enumerated(EnumType.STRING)
    private Gender genero;
    private String direccion;

    @Pattern(regexp = "\\d{10}", message = "El teléfono debe tener 10 dígitos")
    private String telefono;

    @PastOrPresent(message = "La fecha de registro debe ser valida")
    private Date fecRegistro;


    public enum Gender{
        masculino,
        femenino
    }
}
