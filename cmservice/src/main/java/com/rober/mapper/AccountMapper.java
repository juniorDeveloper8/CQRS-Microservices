package com.rober.mapper;

import com.rober.dto.AccountDTO;
import com.rober.model.Account;
import org.springframework.stereotype.Component;

@Component
public class AccountMapper {

    public AccountDTO toDTO(Account account) {

        AccountDTO dto = new AccountDTO();
        dto.setIdAccount(account.getIdAccount());
        dto.setSaldo(account.getSaldo());
        dto.setDeuda(account.getDeuda());
        dto.setFechaCorte(account.getFechaCorte());
        dto.setLimitCredit(account.getLimitCredit());
        dto.setStatus(account.getStatus());
        return dto;
    }
}
