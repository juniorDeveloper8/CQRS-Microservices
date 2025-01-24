package com.rober.dto;

import com.rober.model.Client;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ClientEvent {
    private String eventType;
    private Client client;
}
