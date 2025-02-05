package com.rober.service.impl;

import com.rober.dto.AccountDTO;
import com.rober.dto.AccountEvents;
import com.rober.dto.ClientDTO;
import com.rober.dto.ClientEvent;
import com.rober.exceptions.AccountException;
import com.rober.mapper.AccountMapper;
import com.rober.repository.AccountRepository;
import com.rober.repository.ClientRepository;
import com.rober.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private AccountMapper accountMapper;

    @Autowired
    private ClientRepository clientRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public AccountDTO update(Integer idAccount, AccountDTO accountDTO) {
        try {
            return accountRepository.findById(idAccount)
                    .map(account -> {
                        account.setSaldo(accountDTO.getSaldo());
                        account.setDeuda(accountDTO.getDeuda());
                        account.setFechaCorte(accountDTO.getFechaCorte());
                        account.setLimitCredit(accountDTO.getLimitCredit());
                        account.setStatus(accountDTO.getStatus());
                        AccountDTO savedAccountDTO = accountMapper.toDTO(accountRepository.save(account));
                        kafkaTemplate.send("account-event-topic", new AccountEvents("UpdateCuenta", savedAccountDTO));

                        return savedAccountDTO;
                    }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + idAccount));
        } catch (Exception e) {
            throw new AccountException("Error al actualizar la cuenta: " + e.getMessage());
        }
    }

    @Override
    public String delete(Integer idAccount) {
        try {
            return accountRepository.findById(idAccount)
                    .map(account -> {
                        Integer idClient = account.getClient().getIdClient();
                        accountRepository.deleteById(idAccount);
                        clientRepository.deleteById(idClient);
                        kafkaTemplate.send("account-event-topic", new AccountEvents("DeleteCuenta", new AccountDTO(idAccount)));
                        kafkaTemplate.send("client-event-topic", new ClientEvent("DeleteCliente", new ClientDTO(idClient)));
                        return "El cliente con id: " + idClient + " y su cuenta con id: " + idAccount + " fueron dados de baja correctamente!";
                    }).orElseThrow(() -> new RuntimeException("Cuenta no encontrada con id: " + idAccount));
        } catch (Exception e) {
            throw new AccountException("Error al dar de baja la cuenta: " + e.getMessage());
        }
    }
}
