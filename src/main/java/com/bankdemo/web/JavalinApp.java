package com.bankdemo.web;

import com.bankdemo.application.command.CreateAccountCommandHandler;
import com.bankdemo.application.command.CreateClientCommandHandler;
import com.bankdemo.application.command.DepositCommandHandler;
import com.bankdemo.application.command.WithdrawCommandHandler;
import com.bankdemo.application.query.GetBalanceQueryHandler;
import com.bankdemo.application.query.GetAccountEventsQueryHandler;
import com.bankdemo.domain.command.CreateAccountCommand;
import com.bankdemo.domain.command.CreateClientCommand;
import com.bankdemo.domain.command.DepositCommand;
import com.bankdemo.domain.command.WithdrawCommand;
import com.bankdemo.domain.exception.*;
import com.bankdemo.domain.query.*;
import com.google.inject.Inject;
import com.google.inject.name.Named;
import io.javalin.Javalin;

import java.util.*;

public class JavalinApp {
    private final CreateClientCommandHandler createClientHandler;
    private final CreateAccountCommandHandler createAccountHandler;
    private final DepositCommandHandler depositHandler;
    private final WithdrawCommandHandler withdrawHandler;
    private final GetBalanceQueryHandler balanceQueryHandler;
    private final GetAccountEventsQueryHandler getAccountEventsQueryHandler;

    @Inject
    public JavalinApp(@Named("createClient") CreateClientCommandHandler createClientHandler,
                      @Named("createAccount") CreateAccountCommandHandler createAccountHandler,
                      @Named("deposit") DepositCommandHandler depositHandler,
                      @Named("withdraw") WithdrawCommandHandler withdrawHandler,
                      @Named("getBalance") GetBalanceQueryHandler balanceQueryHandler,
                      @Named("getAccountEvents") GetAccountEventsQueryHandler getAccountEventsQueryHandler){
        this.createClientHandler = createClientHandler;
        this.createAccountHandler = createAccountHandler;
        this.depositHandler = depositHandler;
        this.withdrawHandler = withdrawHandler;
        this.balanceQueryHandler = balanceQueryHandler;
        this.getAccountEventsQueryHandler = getAccountEventsQueryHandler;
    }

    public void start() {
        Javalin app = Javalin.create().start(7000);

        app.post("/clients", ctx -> {
            CreateClientCommand command = ctx.bodyAsClass(CreateClientCommand.class);
            int clientId = createClientHandler.handle(command);
            ctx.status(201).json(clientId);
        });

        app.post("/accounts", ctx -> {
            CreateAccountCommand command = ctx.bodyAsClass(CreateAccountCommand.class);
            int accountId = createAccountHandler.handle(command);
            ctx.status(201).json(accountId);
        });

        app.post("/deposit", ctx -> {
            try{
                DepositCommand command = ctx.bodyAsClass(DepositCommand.class);
                depositHandler.handle(command);
                ctx.status(200).json("Deposito exitoso");
            } catch (BusinessException e) {
                ctx.status(400).json(Collections.singletonMap("error", e.getMessage()));//Bad Request
            }
        });

        app.post("/withdraw", ctx -> {
            try{
                WithdrawCommand command = ctx.bodyAsClass(WithdrawCommand.class);
                withdrawHandler.handle(command);
                ctx.status(200).json("Retiro de dinero exitoso");
            } catch (BusinessException e) {
                ctx.status(400).json(Collections.singletonMap("error", e.getMessage()));//Bad Request
            }
        });

        app.get("/balance/{accountId}", ctx -> {
            int accountId = Integer.parseInt(ctx.pathParam("accountId"));
            double balance = balanceQueryHandler.handle(new GetBalanceQuery(accountId));
            ctx.json("Balance: " + balance);
        });

        app.get("/events/{accountId}", ctx -> {
            int accountId = Integer.parseInt(ctx.pathParam("accountId"));
            ctx.json(getAccountEventsQueryHandler.handle(new GetAccountEventsQuery(accountId)));
        });
    }
}
