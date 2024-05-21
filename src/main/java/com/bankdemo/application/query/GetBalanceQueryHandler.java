package com.bankdemo.application.query;

import com.bankdemo.domain.query.GetBalanceQuery;
import com.bankdemo.domain.repository.BankAccountRepository;

import com.google.inject.Inject;

public class GetBalanceQueryHandler {
    private final BankAccountRepository repository;

    @Inject
    public GetBalanceQueryHandler(BankAccountRepository repository) {
        this.repository = repository;
    }

    public double handle(GetBalanceQuery query) {
        return repository.getBalance(query.getAccountId());
    }
}
