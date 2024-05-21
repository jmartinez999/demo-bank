package com.bankdemo.config;

import com.bankdemo.application.command.*;
import com.bankdemo.application.query.*;
import com.bankdemo.domain.repository.*;
import com.bankdemo.infrastructure.repository.*;
import com.google.inject.AbstractModule;
import com.google.inject.name.Names;

public class GuiceModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(BankAccountRepository.class).to(H2BankAccountRepository.class);
        bind(ClientRepository.class).to(H2ClientRepository.class);

        bind(CreateClientCommandHandler.class).annotatedWith(Names.named("createClient")).to(CreateClientCommandHandler.class);
        bind(CreateAccountCommandHandler.class).annotatedWith(Names.named("createAccount")).to(CreateAccountCommandHandler.class);

        bind(DepositCommandHandler.class).annotatedWith(Names.named("deposit")).to(DepositCommandHandler.class);
        bind(WithdrawCommandHandler.class).annotatedWith(Names.named("withdraw")).to(WithdrawCommandHandler.class);
        bind(GetBalanceQueryHandler.class).annotatedWith(Names.named("getBalance")).to(GetBalanceQueryHandler.class);
        bind(GetAccountEventsQueryHandler.class).annotatedWith(Names.named("getAccountEvents")).to(GetAccountEventsQueryHandler.class);

    }
}
