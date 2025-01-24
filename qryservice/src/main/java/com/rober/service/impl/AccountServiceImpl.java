package com.rober.service.impl;

import com.rober.dto.AccountEvents;
import com.rober.model.Account;
import com.rober.repository.AccountRepository;
import com.rober.service.IAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Service
public class AccountServiceImpl implements IAccountService {

    @Autowired
    private AccountRepository repository;

    @Override
    public Account findByIdAccount(String idAccount) {
        return repository.findByIdAccount(idAccount);
    }

    @Override
    @KafkaListener(topics = "account-event-topic", groupId = "account-event-group")
    public void processAccountEvent(AccountEvents accountEvents) {
        try {
            if (accountEvents == null || accountEvents.getAccount() == null) {
                System.err.println("Mensaje de evento o cuenta nulo recibido");
                return;
            }

            Account account = accountEvents.getAccount();
            String eventType = accountEvents.getEventType();

            switch (eventType) {
                case "CreateCuenta":
                    repository.save(account);
                    break;

                case "UpdateCuenta":
                    Account existingAccount = repository.findById(account.getIdAccount()).orElse(null);
                    if (existingAccount != null) {
                        existingAccount.setDeuda(account.getDeuda());
                        existingAccount.setFechaCorte(account.getFechaCorte());
                        existingAccount.setLimitCredit(account.getLimitCredit());
                        existingAccount.setStatus(account.getStatus());
                        repository.save(existingAccount);
                    } else {
                        System.err.println("Cuenta no encontrada para actualizar: " + account.getIdAccount());
                    }
                    break;

                case "DeleteCuenta":
                    repository.deleteById(account.getIdAccount());
                    break;

                default:
                    System.err.println("Evento desconocido: " + eventType);
                    break;
            }

        } catch (Exception e) {
            System.err.println("Error procesando el evento de cuenta: " + e.getMessage());
            e.printStackTrace();
        }
    }

}