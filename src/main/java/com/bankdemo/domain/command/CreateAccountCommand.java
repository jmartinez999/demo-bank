package com.bankdemo.domain.command;

import com.fasterxml.jackson.annotation.*;

public class CreateAccountCommand {
    private final int clientId;

    @JsonCreator
    public CreateAccountCommand(@JsonProperty("clientId") int clientId) {
        this.clientId = clientId;
    }

    public int getClientId() {
        return clientId;
    }
}
