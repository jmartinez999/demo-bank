package com.bankdemo.domain.repository;

import com.bankdemo.domain.event.DomainEvent;
import com.bankdemo.domain.model.BankAccount;

import java.util.List;

public interface BankAccountRepository {
    void saveEvent(int accountId, DomainEvent event);
    List<DomainEvent> getEvents(int accountId);
    double getBalance(int accountId);
    int createAccount(int clientId);
    boolean accountBelongsToClient(int accountId, String cedula);
}
