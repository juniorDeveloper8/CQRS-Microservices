package com.rober.controller;

import com.rober.dto.ClientDTO;
import com.rober.service.impl.ClientServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/client")
public class ClientController {

    @Autowired
    private ClientServiceImpl service;

    @PostMapping
    public ClientDTO newClient(@RequestBody ClientDTO clientDTO) {
        return service.save(clientDTO);
    }

    @PatchMapping("/{idClient}")
    public ClientDTO updateClient(@PathVariable String idClient, @RequestBody ClientDTO clientDTO) {
        return service.update(idClient, clientDTO);
    }

    @DeleteMapping("/{idClient}")
    public String deleteClient(@PathVariable String idClient) {
        return service.delete(idClient);
    }
}
