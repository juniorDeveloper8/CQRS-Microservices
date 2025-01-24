package com.rober.model;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.Date;

@Entity
@Table(name = "Account")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    private String idAccount;

    private BigDecimal saldo;
    private BigDecimal deuda;
    private BigDecimal limitCredit;
    private Date fechaCorte;
    private Boolean status;
}
