package com.rober.controller;

import com.rober.model.Account;
import com.rober.model.Client;
import com.rober.service.impl.AccountServiceImpl;
import com.rober.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/listener")
public class AppController {

    @Autowired
    private ClientServiceImpl clientService;
    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/client/{idClient}")
    public ResponseEntity<Client> findByIdClient(@PathVariable Integer idClient) {
        if (idClient == null) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        Client client = clientService.findByIdClient(idClient);

        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }

        return ResponseEntity.ok(client);

    }


    @GetMapping("/account/{idAccount}")
    public Account findByIdAccount(@PathVariable Integer idAccount) {
        return accountService.findByIdAccount(idAccount);
    }

}
