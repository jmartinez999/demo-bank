package com.bankdemo.application.command;

import com.bankdemo.domain.command.WithdrawCommand;
import com.bankdemo.domain.event.WithdrawEvent;
import com.bankdemo.domain.exception.*;
import com.bankdemo.domain.repository.BankAccountRepository;
import com.bankdemo.domain.repository.ClientRepository;

import com.google.inject.Inject;

public class WithdrawCommandHandler {
    private final BankAccountRepository accountRepository;
    private final ClientRepository clientRepository;

    @Inject
    public WithdrawCommandHandler(BankAccountRepository accountRepository, ClientRepository clientRepository) {
        this.accountRepository = accountRepository;
        this.clientRepository = clientRepository;
    }

    public void handle(WithdrawCommand command) {
        if (!accountRepository.accountBelongsToClient(command.getAccountId(), command.getCedula())) {
            throw new BusinessException("La cuenta no pertenece al cliente.");
        }
        // Si no tiene suficiente dinero en la cuenta, lanzar una excepción
        if(accountRepository.getBalance(command.getAccountId()) < command.getAmount()) {
            throw new BusinessException("Fondos insuficientes.");
        }
        accountRepository.saveEvent(command.getAccountId(), new WithdrawEvent(command.getAmount()));
    }
}
