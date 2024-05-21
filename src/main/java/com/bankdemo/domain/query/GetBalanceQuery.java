package com.bankdemo.domain.query;

public class GetBalanceQuery {
    private final int accountId;

    public GetBalanceQuery(int accountId) {
        this.accountId = accountId;
    }

    public int getAccountId() {
        return accountId;
    }
}
