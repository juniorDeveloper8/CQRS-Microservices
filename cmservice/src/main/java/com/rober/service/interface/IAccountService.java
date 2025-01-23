package com.rober.service;


import com.rober.dto.AccountDTO;

public interface IAccountService {

    AccountDTO update(String idAccount, AccountDTO accountDTO);

    String delete(String idAccount);

}