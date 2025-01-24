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

@RestController
@RequestMapping("/listener")
public class AppController {

    @Autowired
    private ClientServiceImpl clientService;
    @Autowired
    private AccountServiceImpl accountService;

    @GetMapping("/client/{idClient}")
    public ResponseEntity<Client> findByIdClient(@PathVariable String idClient) {
        // Verificar si idClient está en blanco o es nulo
        if (idClient == null || idClient.trim().isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(null);
        }

        // Intentar obtener el cliente por id
        Client client = clientService.findByIdClient(idClient);
        if (client == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();  // Cliente no encontrado
        }

        return ResponseEntity.ok(client);
    }


    @GetMapping("/account/{idAccount}")
    public Account findByIdAccount(@PathVariable String idAccount) {
        return accountService.findByIdAccount(idAccount);
    }

}
