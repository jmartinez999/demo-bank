package com.bankdemo.application.query;

import com.bankdemo.domain.query.GetAccountEventsQuery;
import com.bankdemo.domain.repository.BankAccountRepository;
import com.bankdemo.domain.event.DomainEvent;

import com.google.inject.Inject;
import java.util.List;

public class GetAccountEventsQueryHandler {
    private final BankAccountRepository repository;

    @Inject
    public GetAccountEventsQueryHandler(BankAccountRepository repository) {
        this.repository = repository;
    }

    public List<DomainEvent> handle(GetAccountEventsQuery query) {
        return repository.getEvents(query.getAccountId());
    }
}
