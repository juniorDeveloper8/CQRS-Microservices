package com.rober.dto;

import com.rober.model.Account;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountEvents {

    private String eventType;
    private Account account;
}
