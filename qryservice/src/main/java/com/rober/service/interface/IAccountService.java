package com.rober.service;

import com.rober.dto.AccountEvents;
import com.rober.model.Account;

public interface IAccountService {

    Account findByIdAccount(Integer idAccount);

    void processAccountEvent(AccountEvents accountEvents);

}