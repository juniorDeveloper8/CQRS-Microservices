package com.rober.service;


import com.rober.dto.AccountDTO;

public interface IAccountService {

    AccountDTO update(Integer idAccount, AccountDTO accountDTO);

    String delete(Integer idAccount);

}