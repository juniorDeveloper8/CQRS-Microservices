package com.rober.model;


import jakarta.persistence.*;
import jakarta.validation.constraints.PastOrPresent;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;
import java.util.UUID;

@Entity
@Table(name = "Account")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "id_account", unique = true, nullable = false, length = 200)
    private String idAccount;

    @ManyToOne
    @JoinColumn(name="id_client")
    private Client client;

    private BigDecimal saldo;
    private BigDecimal deuda;
    private BigDecimal limitCredit;
    @PastOrPresent(message = "La fecha de registro debe ser valida")
    private Date fechaCorte;

    @Column(name = "status")
    private Boolean status;


}
