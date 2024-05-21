package com.bankdemo.application.command;

import com.bankdemo.domain.command.DepositCommand;
import com.bankdemo.domain.event.DepositEvent;
import com.bankdemo.domain.exception.*;
import com.bankdemo.domain.repository.BankAccountRepository;
import com.bankdemo.domain.repository.ClientRepository;

import com.google.inject.Inject;

public class DepositCommandHandler {
    private final BankAccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Inject
    public DepositCommandHandler(BankAccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public void handle(DepositCommand command) {
        if (!accountRepository.accountBelongsToClient(command.getAccountId(), command.getCedula())) {
            throw new BusinessException("La cuenta no pertenece al cliente.");
        }
        accountRepository.saveEvent(command.getAccountId(), new DepositEvent(command.getAmount()));
    }
}
