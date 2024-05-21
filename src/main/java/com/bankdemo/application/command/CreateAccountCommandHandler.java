package com.bankdemo.application.command;

import com.bankdemo.domain.command.CreateAccountCommand;
import com.bankdemo.domain.repository.BankAccountRepository;

import com.google.inject.Inject;

public class CreateAccountCommandHandler {
    private final BankAccountRepository repository;

    @Inject
    public CreateAccountCommandHandler(BankAccountRepository repository) {
        this.repository = repository;
    }

    public int handle(CreateAccountCommand command) {
        return repository.createAccount(command.getClientId());
    }
}
