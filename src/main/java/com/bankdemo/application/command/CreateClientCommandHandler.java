package com.bankdemo.application.command;

import com.bankdemo.domain.command.CreateClientCommand;
import com.bankdemo.domain.repository.ClientRepository;

import com.google.inject.Inject;

public class CreateClientCommandHandler {
    private final ClientRepository repository;

    @Inject
    public CreateClientCommandHandler(ClientRepository repository) {
        this.repository = repository;
    }

    public int handle(CreateClientCommand command) {
        return repository.createClient(command.getName(), command.getCedula());
    }
}
