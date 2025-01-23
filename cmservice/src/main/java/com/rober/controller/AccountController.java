package com.rober.controller;

import com.rober.dto.AccountDTO;
import com.rober.service.impl.AccountServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/account")
public class AccountController {

    @Autowired
    private AccountServiceImpl service;

    @PatchMapping("/{idAccount}")
    public AccountDTO updateAccount(@PathVariable String idAccount, @RequestBody AccountDTO accountDTO) {
        return service.update(idAccount, accountDTO);
    }

    @DeleteMapping("/{idAccount}")
    public String deleteAccount(@PathVariable String idAccount) {
        return service.delete(idAccount);
    }
}
