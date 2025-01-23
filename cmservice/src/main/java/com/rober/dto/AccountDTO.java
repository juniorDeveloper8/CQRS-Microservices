package com.rober.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.Date;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AccountDTO {
    private String idAccount;
    private BigDecimal saldo;
    private BigDecimal deuda;
    private BigDecimal limitCredit;
    private Date fechaCorte;
    private Boolean status;

    public AccountDTO(String idAccount) {
        this.idAccount = idAccount;
    }
}
