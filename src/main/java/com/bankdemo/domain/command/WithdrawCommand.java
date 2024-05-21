package com.bankdemo.domain.command;

import com.fasterxml.jackson.annotation.*;

public class WithdrawCommand {
    private final int accountId;
    private final double amount;
    private final String cedula;

    @JsonCreator
    public WithdrawCommand(@JsonProperty("accountId")int accountId,
                           @JsonProperty("amount")double amount,
                           @JsonProperty("cedula")String cedula) {
        this.accountId = accountId;
        this.amount = amount;
        this.cedula = cedula;
    }

    public int getAccountId() {
        return accountId;
    }

    public double getAmount() {
        return amount;
    }

    public String getCedula() {
        return cedula;
    }
}
